<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
const props = defineProps({
    open_access: { type: Object, required: true }
})

function capitalizeFirstLetter(str: string) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

onBeforeMount(() => {
    console.log(props.open_access)
})
</script>
<style lang="scss" scoped></style>
<template>
    <article class="box p-0 message is-success">
        <div class="message-header">
            <p class="capitalize">Open Access Status: {{ capitalizeFirstLetter(props.open_access.oa_status) }}</p>
        </div>
        <div class="message-body" v-if="props.open_access.is_oa">
            <div class="content">
                <a class="button is-danger m-1"
                    :href="props.open_access.best_oa_location.url_for_pdf ?? props.open_access.first_oa_location.url_for_pdf">
                    <span class="icon is-small">
                        <i class="fas fa-file-pdf"></i>
                    </span>
                    <span>Article PDF</span>
                </a>
                <a class="button is-primary m-1"
                    :href="props.open_access.best_oa_location.url_for_landing_page ?? props.open_access.first_oa_location.url_for_landing_page">
                    <span class="icon is-small">
                        <i class="fas fa-file-alt"></i>
                    </span>
                    <span>Full Text</span>
                </a>
            </div>
        </div>
    </article>
</template>