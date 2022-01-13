<template>
    <div class="row">
        <h4 v-if="result.title">Title: <span class="text-primary">{{result.title}}</span></h4>
        <h4 v-if="result.actors && result.actors.length > 0">Actors:</h4>
        <ul class="list-group" v-if="result.actors && result.actors.length > 0">
            <li v-for="actor in result.actors" :key="actor" class="list-group-item text-success">{{ actor }}</li>
        </ul>
        <h4 v-if="result.system_actors && result.system_actors.length > 0">System actors:</h4>
        <ul class="list-group" v-if="result.system_actors && result.system_actors.length > 0">
            <li v-for="actor in result.system_actors" :key="actor" class="list-group-item text-success">{{ actor }}</li>
        </ul>
        <h4 v-if="result.steps && result.steps.length > 0">Steps:</h4>
        <ul class="list-group" v-if="result.steps && result.steps.length > 0">
            <li v-for="step in result.steps" :key="step" class="list-group-item text-info text-start" v-html="indentStep(step)"></li>
        </ul>
        <h4 v-if="result.keyword_count">Keywords count: <span class="text-primary">{{ result.keyword_count }}</span></h4>
        <h4 v-if="result.steps_count">Steps count: <span class="text-primary">{{ result.steps_count }}</span></h4>
        <h4 v-if="result.invalid_steps && result.invalid_steps.length > 0">Invalid steps:</h4>
        <ul class="list-group" v-if="result.invalid_steps">
            <li v-for="step in result.invalid_steps" :key="step" class="list-group-item text-warning">{{ step }}</li>
        </ul>
        <h4 v-if="result.errors">Errors:</h4>
        <ul class="list-group" v-if="result.errors">
            <li v-for="error in result.errors" :key="error" class="list-group-item text-danger">{{ error }}</li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "ScenarioResult",
    props: {
        value: Object
    },
    data(){
        return {
            result: this.value
        }
    },
    methods: {
        indentStep(step) {
            const arr =  step.split("~")
            return `<span style="margin-left:${50 * parseInt(arr[0])}px">${arr[1]}</span>`
        }
    }

}
</script>