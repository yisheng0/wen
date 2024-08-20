import { defineStore } from "pinia";
import { ref} from "vue";

export const useTabsStore = defineStore("tabs", () => {
  let tabs = ref([]);
  let currentTab = ref(null);

  function addTab(tab) {
    currentTab.value = tab;
    if (tabs.value.includes(tab)) {
      return;
    }
    tabs.value.push(tab);
  }
  function removeTab(tab) {
    if (currentTab.value === tab) {
        let index = tabs.value.indexOf(tab);
        currentTab.value = tabs.value[index - 1] || tabs.value[index + 1];
        if (currentTab.value) {
          setCurrentTab(currentTab.value);
        }
      }
     tabs.value = tabs.value.filter((t) => t !== tab);
  }
  function setCurrentTab(tab) {
    currentTab.value = tab;
  }
  return { currentTab, tabs, addTab, removeTab, setCurrentTab };
});
