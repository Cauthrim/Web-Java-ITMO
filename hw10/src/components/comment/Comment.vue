<template>
    <div class="comment">
        <div class="title">{{comment.id}}</div>
        <div class="information">By {{findUser(comment)}}</div>
        <div class="body">{{comment.text}}</div>
    </div>
</template>

<script>
export default {
    name: "Comment",
    props: ["comment", "users"],
    methods: {
        findUser: function(comment) {
            let res = "";
            for (let user of Object.values(this.users)) {
                if (user.id === comment.userId) {
                    res = user.login;
                }
            }

            return res;
        }
    }
}
</script>

<style scoped>

</style>