window.onload = function() {
	initApp();
};

/*Vue.component('button-counter', {
	data: function() {
		return {
			count: 0,
		};
	},
	template:
		`<button v-on:click="count++">Click counter - {{ count }}</button>`,
});*/

Vue.component('common-chat', {
	data() {
        return {
            ws: null,
            message: "",
            messages: [],
        };
    },
    mounted() {
        // this.createSocket();
    },
    created() {
        // var _this = this;
        //this.onMessage();
        this.createSocket();
        //this.onMessage();
        //this.sendMessage();
    },
    methods: {
        sendMessage() {
            console.log(this.isOpen(this.ws));
            this.ws.send(this.message);
        },
        onMessage() {
            console.log("onMessage()", this.ws.onmessage);
        },
        createSocket() {
            this.ws = new WebSocket("ws://localhost:8080/websocket");
            console.log(this.ws);
            var _this = this;
            this.ws.onmessage = function (event) {
                console.log("event data", event.data);
                console.log("message", _this.message);
                _this.messages.push(event.data);
            };
        },
        isOpen(ws) {
            return ws.readyState === ws.OPEN;
        },
    },
	template:
		`<div class='common-chat'>
            <textarea v-model="message"></textarea>
            <input type="button" value="sendmessage" v-on:click="sendMessage" />
            <ul v-for="(m, index) in messages" :key="index">
                <li>{{ m }}</li>
            </ul>
        </div>`,
});

Vue.component('participant-list', {
	props: ["bus"],
    data() {
        return {
            eventBus: this.bus,
            participantList: [{id: 1, title: "hello"}, {id: 2, title: "cat mat"}, {id: 3, title:"cat dog"}],
            addMsg: "Add participant",
            deleteMsg: "Delete participant",
            startMsg: "Start",
            name: "Input Name",
            count: 4,
        };
    },
    methods: {
        addParticipant() {
            this.participantList.push({title: this.createName()});
        },
        createName() {
            return this.name + " #" + (this.count++);
        },
        deleteRow(participant) {
            var index = this.participantList.indexOf(participant);
            this.deleteRowByIndex(index);
        },
        deleteRowByIndex(index) {
            this.$children[index].stopTimer();
            console.log("Delete row before", this.participantList, index);
            this.participantList.splice(index, 1);
            //Array.prototype.splice.apply(this.participantList, index, 1);
            console.log("Delete row after", this.participantList);
        }
      },
	template:
		`<div class='participant-list'>
            <strong style="text:bold">Timer Participants:</strong>
            <a class="button" v-on:click="addParticipant">{{ addMsg }}</a>
            <ul v-for="(participant, participantId) in participantList" :key="participantId" :row-data="participant">
                <li>
                    <row v-on:delete-row-event="deleteRow" v-bind:participant="participant"></row>
                    <!--<input type="button" v-on:click="deleteRowByIndex(index)" value="Delete"/>-->
                </li>
            </ul>
        </div>`,
});

Vue.component('row', {
	props: ["participant"],
    data() {
        return {
            addMsg: "Add participant",
            deleteMsg: "Delete participant",
            startMsg: "Start",
            name: "Input Name",
            //partic: this.participant,
        };
    },
    unmounted() {
        console.log("Row unmounted");
    },
    deactivated() {
        console.log("Row deactivated");
    },
    destroyed() {
        console.log("Row destroyed", this.participant.title);
        this.stopTimer();
    },
    methods: {
        deleteParticipant() {
            console.log("delete", this.participant.title);
            this.$emit("delete-row-event", this.participant)
        },
        startTimer() {
            console.log("start timer", this.$children);
            //this.eventBus.$emit("participant-start-timer-event", this.index);
            //this.$children[index].start();
            for (var i = 0; i < this.$children.length; i++) {
                if ('start' in this.$children[i]) {
                    this.$children[i].start();
                }
            }
        },
        stopTimer() {
            console.log("stop timer");
            for (var i = 0; i < this.$children.length; i++) {
                if ('stop' in this.$children[i]) {
                    this.$children[i].stop();
                }
            }
        }
    },
	template:
		`<div class='row'>
            <input type="text" v-bind:value="participant.title" />
            <timer :participant="participant"></timer>
            <row-info :participant="participant"></row-info>
            <a class="button" v-on:click="startTimer()">{{startMsg}}</a>
            <a class="button" v-on:click="deleteParticipant()">{{deleteMsg}}</a>
        </div>`,
});

Vue.component('row-info', {
	props: ["participant"],
    data() {
        return {
            text: "text",
        };
    },
    methods: {
    },
	template:
		`<div class='row-info' style="display:inline-block">
            <input type="text" v-model="text" />
         </div>`,
});

Vue.component('timer', {
	props: ["participant"],
    data() {
        return {
            minutes: 20000,
            seconds: 100,
            interval: null,
            partic: this.participant,
        };
    },
    // mounted() {
        //   var _this = this;
        //   console.log("mounted");
        //   this.eventBus.$on("participant-start-timer-event", function(event) {_this.count(); console.log(event)});
    // },
    unmounted() {
        console.log("Timer unmounted");
    },
    deactivated() {
        console.log("Timer deactivated");
    },
    destroyed() {
        console.log("Timer destroyed");
    },
    methods: {
        count() {
            console.log("count");
            if (this.seconds != 0) {
                this.seconds--;
            } else if (this.minutes != 0) {
                this.minutes--;
                this.seconds = 59;
            } else {
                this.stop();
            }
        },
        tick() {
            this.count();
        },
        start() {
            console.log("start");
            var _this = this;
            this.interval = setInterval(function () {
                _this.tick();
            }, 1000);
        },
        stop() {
            console.log("stop", this.interval);
            if (this.interval == null) return;
            clearInterval(this.interval);
            this.interval = null;
        },
    },
	template:
		`<div class='timer' style="display:inline-block">
            <input type="text" v-model="seconds" />
            <input type="text" v-model="participant.title" />
        </div>`,
});

// Entry point on room page.
function initApp() {
    console.log("start vue app");

    window.app = new Vue({
        el: "#app",
        data() {
            return {
              bus: new Vue(),
              commonRoom: false
            }
          },
          methods: {
            goToCommonRoom() {
              console.log("go to room")
              this.commonRoom = true;
            },
          },
        template:
            `<div class='app'>
                <input  v-if="commonRoom == false" type="button" value="go to chat" v-on:click="goToCommonRoom"/>
                <div v-if="commonRoom == true">
                    <!--<common-chat></common-chat>-->

                </div>
                <participant-list v-bind:bus="bus"></participant-list>
                <!--<timer></timer>-->
            </div>`,
    });
}
