package com.mycompany.myapp.service;

import java.time.Instant;

public class Action {

    private String id;
    private String idMemberCreator;
    private String type;
    private Instant date;
    private Data data;

    private MemberCreator memberCreator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMemberCreator() {
        return idMemberCreator;
    }

    public void setIdMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public MemberCreator getMemberCreator() {
        return memberCreator;
    }

    public void setMemberCreator(MemberCreator memberCreator) {
        this.memberCreator = memberCreator;
    }

    public class MemberCreator {

        private String id;
        private String userName;
        private String avatarHash;
        private String fullName;
        private String initials;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatarHash() {
            return avatarHash;
        }

        public void setAvatarHash(String avatarHash) {
            this.avatarHash = avatarHash;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getInitials() {
            return initials;
        }

        public void setInitials(String initials) {
            this.initials = initials;
        }
    }
}
