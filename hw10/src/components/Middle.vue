<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :users="users" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <SeparatePost v-if="page === 'SeparatePost'" :post="post" :users="users" :comments="Object.values(comments).filter(p => p.postId === post.id)"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import Register from "./page/Register";
import Users from "./page/Users";
import SeparatePost from "./post/SeparatePost";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
        WritePost,
        Enter,
        Index,
        Register,
        Users,
        SeparatePost,
        Sidebar,
        EditPost
    },
    props: ["posts", "users", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("onSeparatePost", (page, post) => {this.page = page; this.post = post})
    }
}
</script>

<style scoped>

</style>
