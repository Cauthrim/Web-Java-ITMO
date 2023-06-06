<template>
    <div>
        <li v-for="post in Object.values(posts).sort((a, b) => b.id - a.id)" :key="post.id">
            <Post :post="post" :users="users" :comments="Object.values(comments).filter(p => p.postId === post.id)"/>
        </li>
    </div>
</template>

<script>
import Post from "../post/Post.vue";

export default {
    name: "Index",
    props: ["posts", "users", "comments"],
    components: {
        Post
    }
}
</script>

<style scoped>
    li {
        list-style-type: none;
    }
</style>
