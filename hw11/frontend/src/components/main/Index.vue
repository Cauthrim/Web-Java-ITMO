<template>
    <div>
        <div v-for="post in Object.values(posts).sort((a, b) => b.id - a.id)" :key="post.id">
            <Post :post="post"/>
        </div>
    </div>
</template>

<script>
import Post from "../post/Post";

export default {
    name: "Index",
    props: ["posts"],
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
