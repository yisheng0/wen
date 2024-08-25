<script setup>
import { Document } from '@element-plus/icons-vue'
const { title, updateTime } = defineProps({
  title: String,
  updateTime: String
})
function formatDate(dateString) {
  const currentYear = new Date().getFullYear();
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  if (year === currentYear) {
    return `${month}-${day} ${hours}:${minutes}`;
  } else {
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  }
}
</script>

<template>
  <div class="card">
    <el-icon style="width: 60px; height: 60px;">
      <Document style="width: 60px; height: 60px; color:#4175E4" />
    </el-icon>
    <div class="card__date">
      <div style="margin-bottom: 5px; font-size: 1.2rem; font-weight: bold; ">{{ title }}</div><br>
      最近修改于 {{ formatDate(updateTime) }}
    </div>
  </div>
</template>
<style scoped>
.card {
  --border-radius: 0.75rem;
  --primary-color: #7257fa;
  --secondary-color: #3c3852;
  width: 136px;
  height: auto;
  font-family: "Arial";
  padding: 1rem;
  cursor: pointer;
  border-radius: var(--border-radius);
  background: #f1f1f3;
  box-shadow: 0px 8px 16px 0px rgb(0 0 0 / 3%);
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center
}

.card>*+* {
  margin-top: 1.1em;
}

.card .card__date {
  color: #6e6b80;
  font-size: 0.8rem;
}
</style>