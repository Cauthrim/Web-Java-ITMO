<template>
    <div class="separatePost">
        <Post :post="post"/>
        <WriteComment :post="post"/>
        <li v-for="comment in post.comments" :key="comment.id">
            <Comment :comment="comment"/>
        </li>
    </div>
</template>

<script>
import Post from "./Post";
import Comment from "../comment/Comment";
import WriteComment from "../comment/WriteComment";

export default {
    name: "SeparatePost",
    props: ["post"],
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