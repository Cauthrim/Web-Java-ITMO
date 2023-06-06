<template>
    <div class="form">
        <div class="header">Write Comment</div>
        <div class="body">
            <form @submit.prevent="onWriteComment">
                <div class="field">
                    <div class="name">
                        <label for="text">Text:</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" v-model="text"></textarea>
                    </div>
                </div>
                <div class="error">{{ error }}</div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    name: "WriteComment",
    data: function () {
        return {
            text: "",
            error: ""
        }
    },
    props: ["post"],
    methods: {
        onWriteComment: function () {
            this.error = "";
            this.$root.$emit("onWriteComment", this.text, this.post);
        }
    },
    beforeMount() {
        this.$root.$on("onWriteCommentValidationError", error => this.error = error);
    }
}
</script>

<style scoped>

</style>