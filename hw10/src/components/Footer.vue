<template>
    <footer>
      <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov Number of posts: {{postsCount}}, Number of registered users: {{usersCount}}
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["usersCount", "postsCount"],
}
</script>

<style scoped>

</style>