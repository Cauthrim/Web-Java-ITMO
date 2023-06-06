<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <SeparatePost v-if="page === 'SeparatePost'" :post="post"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import SeparatePost from "./post/SeparatePost";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./page/Users";
import WritePost from "./page/WritePost";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
        Users,
        Register,
        Enter,
        Index,
        Sidebar,
        SeparatePost,
        WritePost
    },
    props: ["posts", "users"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    },
    beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("onSeparatePost", (page, post) => {this.page = page; this.post = post})
    }
}
</script>

<style scoped>

</style>
