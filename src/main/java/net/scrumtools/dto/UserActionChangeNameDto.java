package net.scrumtools.dto;

public class UserActionChangeNameDto extends UserActionDto {
    protected String newUserName;

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    @Override
    public String toString() {
        return "UserActionChangeNameDto{" +
                "newUserName='" + newUserName + '\'' +
                ", roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
