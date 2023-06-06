<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :posts="posts" :users="users"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            componentKey: 0,
            user: null,
            users: [],
            posts: []
        }
    },
    beforeMount() {
        if (localStorage.getItem("jwt") && !this.user) {
            this.$root.$emit("onJwt", localStorage.getItem("jwt"));
        }

        axios.get("/api/1/posts").then(response => {
            this.posts = response.data;
        });

        axios.get("/api/1/users").then(response => {
            this.users = response.data;
        });
    },
    beforeCreate: function () {
        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwt", {
                login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onJwt", (jwt) => {
            localStorage.setItem("jwt", jwt);

            axios.get("/api/1/users/auth", {
                params: {
                    jwt
                }
            }).then(response => {
                this.user = response.data;
                this.$root.$emit("onChangePage", "Index");
            }).catch(() => this.$root.$emit("onLogout"))
        });

        this.$root.$on("onRegister", (login, name) => {
            login = login.trim();
            name = name.trim();
            if (!name) {
                this.$root.$emit("onRegisterValidationError", "Name must not be empty");
            } else if (!login) {
                this.$root.$emit("onRegisterValidationError", "Login must not be empty");
            } else {
                axios.post("/api/1/users", {login, name}).then(response => {
                    axios.get("/api/1/users").then(response => {
                        this.users = response.data;
                    });
                    this.$root.$emit("onJwt", response.data);
                }).catch(error => {
                    this.$root.$emit("onRegisterValidationError", error.response.data);
                });
            }
        });

        this.$root.$on("onWritePost", (title, text) => {
            if (this.user != null) {
                title = title.trim();
                text = text.trim();
                const userId = this.user.id;
                if (!title) {
                    this.$root.$emit("onWritePostValidationError", "Title must not be empty");
                } else if (!text) {
                    this.$root.$emit("onWritePostValidationError", "Text must not be empty");
                } else {
                    axios.post("/api/1/posts", {title, text, userId}).then(response => {
                        axios.get("/api/1/posts").then(response => {
                            this.posts = response.data;
                        });
                        this.$root.$emit("onChangePage", "Index");
                        response;
                    }).catch(error => {
                        this.$root.$emit("onWritePostValidationError", error.response.data);
                    });
                }
            } else {
                this.$root.$emit("onWritePostValidationError", "No access");
            }
        });

        this.$root.$on("onWriteComment", (text, post) => {
            text = text.trim();
            if (this.user) {
                const userId = this.user.id;
                const postId = post.id;
                axios.post("/api/1/comments", {text, userId, postId}).then(response => {
                    axios.get("/api/1/posts").then(response => {
                        this.posts = response.data;
                        this.$root.$emit("onSeparatePost", "SeparatePost", response.data.find(p => p.id === postId));
                    });
                    response;
                }).catch(error => {
                    this.$root.$emit("onWriteCommentValidationError", error.response.data);
                })
            } else {
                this.$root.$emit("onWriteCommentValidationError", "No access");
            }
        });

        this.$root.$on("onLogout", () => {
            localStorage.removeItem("jwt");
            this.user = null;
        });

    }
}
</script>

<style>
#app {

}
</style>
