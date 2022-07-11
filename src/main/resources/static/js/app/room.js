window.onload = function() {
	initApp();
};

var totalTimerInterval = null;
var roomStateServerPollingInterval = null;

function milliToStr(timeMs) {
    var seconds = Math.floor(timeMs/1000) % 60;
    var minutes = Math.floor(timeMs/1000/60) % 60;
    var hours = Math.floor(timeMs/1000/60/60);

    var timeToStr = (t) => t < 10 ? "0" + t : t;

    return (hours <= 0 ? "" : timeToStr(hours) + ":") +
                              timeToStr(minutes) + ":" +
                              timeToStr(seconds);
}
function updateInterval() {
    setInterval(1000);
    roomState.totalTime + 1000;
}

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
            participantList: [],
            addMsg: "Add participant",
            deleteMsg: "Delete participant",
            startMsg: "Start",
            name: "Input Name",
            lastParticipantId: 0,
        };
    },
    methods: {
        addParticipant() {
            this.participantList.push(this.createParticipant());
        },
        createParticipant() {
            var participantId = this.lastParticipantId++;
            return {
                id: participantId,
                title: "bot #" + participantId,
            };
        },
        deleteRow(participant) {
            var index = this.participantList.indexOf(participant);
            console.log("DeleteRow(), participant id " + participant.id);
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
            <ul v-for="(participant, id) in participantList" :key="id" :row-data="participant">
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
              commonRoom: false,
              roomState: null,
              milliToStr: window.milliToStr,
            }
        },
        mounted() {
            console.log("roomId in mounted" + roomId);
            fetch('/room-state/' + roomId)
                .then(response => response.json())
                .then(data => {
                    console.log("initRoomState ", data);
                    this.updateRoomState(data);
                    this.checkAnonymousUser();
                    this.startRoomStateServerPolling();
                });
        },
        methods: {
            isMyUser(userId) {
                console.log("isMyUser");
                return window.userId == userId;
            },
            isOvertime(time) {
                return time.totalTime >= time.timerLimit;
            },
            checkAnonymousUser() {
                var currentUser = this.getCurrentUser();
                if (currentUser.anonymous == true) {
                    let userName = prompt("Please enter your name", "Anonymous");
                    this.changeUserName(userName);
                }
            },
            isCurrentUser() {
                for (var i = 0; i < this.roomState.users.length; i++) {
                    if (this.roomState.users[i].userId == window.userId) {
                        return true;
                    }
                }
                return false;
            },
            getCurrentUser() {
                for (var i = 0; i < this.roomState.users.length; i++) {
                    if (this.roomState.users[i].userId == window.userId) {
                        return this.roomState.users[i];
                    }
                }
                return null;
            },
            changeUserName(name) {
                var formData = new FormData();
                formData.append('userName', name);
                fetch('/room/' + roomId + '/name-changing', {
                    method: 'POST',
                    body: formData,
                }).then(response => response.json())
                .then(data => {
                    this.updateRoomState(data);
                });
            },
            goToCommonRoom() {
                console.log("go to room")
                this.commonRoom = true;
            },
            copyLink() {
                var url = window.location.href;
                navigator.clipboard.writeText(url);
                console.log("url", url);
            },
            startAction() {
                console.log("startAction");
                fetch('/room/' + roomId + '/start-action', {
                    method: 'POST',
                }).then(response => response.json())
                .then(data => {
                    console.log("start ", data);
                    this.updateRoomState(data);
                });
            },
            stopAction() {
                console.log("stop action");
                fetch('/room/' + roomId + '/stop-action', {
                    method: 'POST',
                }).then(response => response.json())
                .then(data => {
                    console.log("stop ", data);
                    this.updateRoomState(data);
                });
            },
            pauseAction() {
                console.log("pause action");
                fetch('/room/' + roomId + '/pause-action', {
                    method: 'POST',
                }).then(response => response.json())
                .then(data => {
                    console.log("pause ", data);
                    this.updateRoomState(data);
                });
            },
            updateRoomState(data) {
                this.roomState = data;
                var _this = this;
                console.log("updateRoomState()");
                if (data.totalTimer.running && totalTimerInterval == null) {
                    totalTimerInterval = setInterval(function() {
                        _this.updateTimer();
                        <!-- console.log("seconds are running"); -->
                    }, 1000);
                    console.log("timer is started");
                } else if (!data.totalTimer.running && totalTimerInterval != null) {
                    clearInterval(totalTimerInterval);
                    totalTimerInterval = null;
                    console.log("timer is stopped");
                }
            },
            updateTimer() {
                if (this.roomState.totalTimer.running) {
                    this.roomState.totalTimer.totalTime += 1000;
                    for (var i = 0; i < this.roomState.users.length; i++) {
                        if (this.roomState.users[i].timer.running) {
                            <!--console.log("user ", this.roomState.users[i]); -->
                            this.roomState.users[i].timer.totalTime += 1000;
                        }
                    }
                }
            },
            startRoomStateServerPolling() {
                var _this = this;
                if (roomStateServerPollingInterval == null) {
                    roomStateServerPollingInterval = setInterval(function() {
                        _this.roomStateServerPoll();
                    }, 1000);
                }
            },
            roomStateServerPoll() {
                var _this = this;
                var formData = new FormData();
                formData.append('lastUpdatedDate', this.roomState.lastUpdatedDate);
                fetch('/room-state/' + roomId + '/poll', {
                    method: 'POST',
                    body: formData,
                }).then(response => response.json())
                .then(data => {
                    if (JSON.stringify(data) != '{}') {
                        _this.updateRoomState(data);
                    }
                });
            },
        },
        template:
            `<div class='app container'>
                <div class="header">
                </div>
                <div class="content">
                    <h3>{{roomState.name}}</h3>
                    <!--input  v-if="commonRoom == false" type="button" value="go to chat" v-on:click="goToCommonRoom"/>-->
                    <!--<div v-if="commonRoom == true">-->
                        <!--<common-chat></common-chat>-->
                    <!--</div>-->
                    <!--<participant-list v-bind:bus="bus"></participant-list>-->
                    <!--<timer></timer>-->
                    <div v-if="roomState != null">
                        <!--Created date: {{roomState.createdDate}}</br>-->
                        <!--Last updated date: {{roomState.lastUpdatedDate}}</br>-->
                        <!--Total timer</br>-->
                        <!--Running: {{roomState.totalTimer.running}}</br>-->
                        <!--Start time: {{milliToStr(roomState.totalTimer.startTime)}}</br>-->
                        Total time: {{milliToStr(roomState.totalTimer.totalTime)}}</br>
                        Users</br>
                        <ul v-for="(user, index) in roomState.users" :key="index">
                            <li v-bind:class="{ 'my-user-highlight': isMyUser(user.userId), 'speaking-user': user.timer.running }">
                                {{user.name}}</br>
                                <!--Running: {{user.timer.running}}</br>-->
                                <!--Start time: {{milliToStr(user.timer.startTime)}}</br>-->
                                Total time: <label v-bind:class="{ 'overtime': isOvertime(user.timer) }">{{milliToStr(user.timer.totalTime)}}</label></br>
                            </li>
                        </ul>
                    </div>
                    <!--
                    <div class="timer-state">
                        <div class="timer-limit">
                            <div>Time limit</div>
                            <timer></timer>
                        </div>
                        <div class="total-time">
                            <div>Tolal time</div>
                            <div>00:00</div>
                        </div>
                    </div>
                    -->
                </div>
                <div class="footer">
                    <div class="control-buttons">
                        <input id="start-btn" type="button" value="Start" v-on:click="startAction"/>
                        <input id="pause-btn" type="button" value="Pause" v-on:click="pauseAction"/>
                        <input id="stop-btn" type="button" value="Stop" v-on:click="stopAction"/>
                        <input id="copy-url-btn" type="button" value="Copy room link" v-on:click="copyLink"/>
                    </div>
                </div>
            </div>`,
    });
}
