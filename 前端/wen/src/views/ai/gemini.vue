<script setup>
import { GoogleGenerativeAI } from "@google/generative-ai";
import { ref, watch ,onUpdated} from "vue"


let article = ref("")
let text = ref("")
let list = ref([]); 
let container = ref()
// AIzaSyAMsuLLChMqilSiCu9ynWGKeUyy56XbEg0

const genAI = new GoogleGenerativeAI("AIzaSyAMsuLLChMqilSiCu9ynWGKeUyy56XbEg0");

async function run() {
    const model = genAI.getGenerativeModel({ model: "gemini-pro" });

    const prompt = article.value
    article.value = ""
    list.value.push({
        value: prompt,
        type: 0
    })
    scrollToBottom(container.value)
    const result = await model.generateContent(prompt);
    const response = result.response;
    text.value = response.text();
    list.value.push({
        value: text.value,
        type: 1
    })
    scrollToBottom(container.value)
}
function scrollToBottom(div) {
    // console.log(1)
    div.scrollTop = div.scrollHeight;
}



</script>

<template>
    <el-card class="page-container">
        <div class="enter">
            <el-input v-model="article"  @keydown.enter="run" placeholder="Enter 发送" type="textarea" class="input" />
            <el-button type="primary" plain @click="run">发送</el-button>
            <el-button type="primary" plain @click="list = []">清空</el-button>
        </div>

        <div class="container" ref="container">
            <template v-for="item in list">

                <div class="left" v-if="item.type === 0" v-bind:key="item.value">
                    <el-card shadow="always">
                        <el-avatar> user </el-avatar> {{ item.value }}
                    </el-card>
                </div>
                <div class="right" v-else v-bind:key="item">
                    <el-card shadow="always">
                        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />{{ item.value
                        }}
                    </el-card>
                </div>
            </template>
        </div>

    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    height: 100%;
    box-sizing: border-box;
}

.container {
    display: flex;
    flex-direction: column;
    height: 52vh;
    overflow-y: scroll;
    background-color: #F3F6FC;
    border-radius: 5%;
    padding-top: 5%;
    padding-bottom: 5%;
}

.left {
    align-self: flex-start;
    border: 2px solid rgb(206, 206, 243);
    width: 60vw;
    border-radius: 4%;
    margin-bottom: 4vh;
}

.right {
    align-self: flex-end;
    border: 2px solid rgb(17, 14, 16);
    width: 60vw;
    border-radius: 4%;
    margin-bottom: 4vh;
}

.input {
    border-radius: 2px, 0px, 0px, 2px;
    width: 70vw;
}

.el-button {
    height: 51.98px;
    width: 5vw;
    border-radius: 0px, 2px, 2px, 0px;
}

.enter {
    display: flex;
    justify-content: center;
    position: fixed;
    top: 82vh;
}
</style>