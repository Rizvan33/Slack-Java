package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action implements Serializable {

    private List<Object> content = new ArrayList<>();
    private Type actionType;
    private Map<ParameterKey, String> actionParameters = new HashMap<>();

    public Action(Object content, Type actionType, Map<ParameterKey, String> actionParameters) {
        this.content.add(content);
        this.actionType = actionType;
        if (actionParameters != null)
            this.actionParameters = actionParameters;
    }

    public Action(List<Object> contentTab, Type actionType, Map<ParameterKey, String> actionParameters) {
        this.content = contentTab;
        this.actionType = actionType;
        if (actionParameters != null)
            this.actionParameters = actionParameters;
    }

    public void setType(Type type) {
        actionType = type;
    }

    public void addParameters(ParameterKey parameterKey, String param) {
        actionParameters.put(parameterKey, param);
    }

    public String getPseudo() {
        return actionParameters.get(ParameterKey.PSEUDO);
    }

    public String getEmail() {
        return actionParameters.get(ParameterKey.EMAIL);
    }

    public String getPassword() {
        return actionParameters.get(ParameterKey.PASSWORD);
    }

    public String getChannelIp() {
        return actionParameters.get(ParameterKey.CHANNEL_IP);
    }

    public String getChannelTitle() {
        return actionParameters.get(ParameterKey.CHANNEL_TITLE);
    }

    public String getChannelPrivacy() {
        return actionParameters.get(ParameterKey.IS_PRIVATE);
    }

    public String getMessageContent() {
        return actionParameters.get(ParameterKey.MESSAGE_CONTENT);
    }

    public Object getContent() {
        return content.get(0);
    }

    public Type getActionType() {
        return actionType;
    }

    public boolean isSuccess() {
        return actionType == Type.SUCCESS;
    }

    public enum Type {
        CREATE_USER,
        CREATE_CHANNEL,
        DELETE_CHANNEL,
        DELETE_USER,
        JOIN_CHANNEL,
        INVITE_CHANNEL,
        KICK_CHANNEL,
        LEAVE_CHANNEL,
        CHANNEL_EXISTS,
        CONNECT_USER,
        DISCONNECT_USER,
        SEND_MESSAGE,
        UPLOAD_FILE,
        DELETE_MESSAGE,
        SEARCH_USER,
        CHANNEL_USERS,
        CHANNEL_MESSAGES,
        USER_CHANNELS,
        SUCCESS,
        FAIL,
        LIST_USERS,
    }

    public enum ParameterKey {
        PSEUDO,
        EMAIL,
        PASSWORD,
        CHANNEL_IP,
        CHANNEL_TITLE,
        MESSAGE_CONTENT,
        IS_PRIVATE,
    }
}
