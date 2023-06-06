<template>
    <div class="separatePost">
        <Post :post="post" :users="users" :comments="comments"/>
        <WriteComment :post="post"/>
        <li v-for="comment in Object.values(comments).sort((a, b) => b.id - a.id)" :key="comment.id">
            <Comment :comment="comment" :users="users"/>
        </li>
    </div>
</template>

<script>
import Post from "./Post";
import Comment from "../comment/Comment";
import WriteComment from "../comment/WriteComment";

export default {
    name: "SeparatePost",
    props: ["post", "users", "comments"],
    components: {
        Post,
        Comment,
        WriteComment
    }
}
</script>

<style scoped>
    li {
        list-style-type: none;
    }
</style>