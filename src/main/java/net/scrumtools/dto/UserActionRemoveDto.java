package net.scrumtools.dto;

public class UserActionRemoveDto extends UserActionDto {
    protected String removingUserId;

    public String getRemovingUserId() {
        return removingUserId;
    }

    public void setRemovingUserId(String removingUserId) {
        this.removingUserId = removingUserId;
    }

    @Override
    public String toString() {
        return "UserActionRemoveDto{" +
                "roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                ", removingUserId='" + removingUserId + '\'' +
                '}';
    }
}
