<template>
    <div class="comment">
        <div class="title">{{comment.id}}</div>
        <div class="information">By {{comment.user.login}}</div>
        <div class="body">{{comment.text}}</div>
    </div>
</template>

<script>
export default {
    name: "Comment",
    props: ["comment"]
}
</script>

<style scoped>

</style>