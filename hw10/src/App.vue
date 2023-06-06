<template>
    <div id="app">
        <Header :userId="userId" :users="users"/>
        <Middle :posts="posts" :users="users" :comments="comments"/>
        <Footer :postsCount="Object.values(posts).length" :usersCount="Object.values(users).length"/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return this.$root.$data;
    },
    beforeCreate() {
        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            const users = Object.values(this.users).filter(u => u.login === login);
            if (users.length === 0) {
                this.$root.$emit("onEnterValidationError", "No such user");
            } else {
                this.userId = users[0].id;
                this.$root.$emit("onChangePage", "Index");
            }
        });

        this.$root.$on("onLogout", () => this.userId = null);

        this.$root.$on("onWriteComment", (text, post) => {
            text = text.trim();
            if (this.userId) {
                if (!text || text.length > 255) {
                    this.$root.$emit("onWriteCommentValidationError", "Text must be between 1 and 255 characters long");
                } else {
                    const id = Math.max(...Object.keys(this.comments)) + 1;
                    this.$root.$set(this.comments, id, {
                        id, text, userId: this.userId, postId: post.id
                    });
                }
            } else {
                this.$root.$emit("onWriteCommentValidationError", "No access");
            }
        });

        this.$root.$on("onWritePost", (title, text) => {
            if (this.userId) {
                if (!title || title.length < 5) {
                    this.$root.$emit("onWritePostValidationError", "Title is too short");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onWritePostValidationError", "Text is too short");
                } else {
                    const id = Math.max(...Object.keys(this.posts)) + 1;
                    this.$root.$set(this.posts, id, {
                        id, title, text, userId: this.userId
                    });
                }
            } else {
                this.$root.$emit("onWritePostValidationError", "No access");
            }
        });

        this.$root.$on("onRegister", (login, name) => {
            login = login.trim();
            name = name.trim();
            if (!name || name.length > 32) {
                this.$root.$emit("onRegisterValidationError", "Name must be between 1 and 32 characters long");
            } else if (!login || login.length < 3 || login.length > 16) {
                this.$root.$emit("onRegisterValidationError", "Login must be between 3 and 16 characters long");
            } else if (!(/^[a-z]*$/.test(login))) {
                this.$root.$emit("onRegisterValidationError", "Login must contain only lower-case English letters");
            } else {
                for (let value of Object.values(this.users)) {
                    if (value.login === login) {
                        this.$root.$emit("onRegisterValidationError", "Login must be unique");
                        return;
                    }
                }
                const id = Math.max(...Object.keys(this.users)) + 1;
                this.$root.$set(this.users, id, {
                    id, login, name, admin: false
                });
                this.$root.$emit("onChangePage", "Enter");
            }
        });

        this.$root.$on("onEditPost", (id, text) => {
            if (this.userId) {
                if (!id) {
                    this.$root.$emit("onEditPostValidationError", "ID is invalid");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onEditPostValidationError", "Text is too short");
                } else {
                    let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                    if (posts.length) {
                        posts.forEach((item) => {
                            item.text = text;
                        });
                    } else {
                        this.$root.$emit("onEditPostValidationError", "No such post");
                    }
                }
            } else {
                this.$root.$emit("onEditPostValidationError", "No access");
            }
        });
    }
}
</script>

<style>
#app {

}
</style>
