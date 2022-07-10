package com.academy.entity.user;

public enum Level {

    SIMPLE {
        @Override
        public UserLevel get() {
            return new UserLevel("SIMPLE");
        }
    },

    MANAGER {
        @Override
        public UserLevel get() {
            return new UserLevel("MANAGER");
        }
    },

    ADMIN {
        @Override
        public UserLevel get() {
            return new UserLevel("ADMIN");
        }
    };

    public abstract UserLevel get();

    }
