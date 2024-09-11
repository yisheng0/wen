import request from "@/utils/request";
export const sparkAddSession = (session) => {
  let content =  JSON.stringify(session.content)
  console.log(content)
  return request.post("/ai", {
    content,
  });
};

export const sparkGetSession = () => {
  return request.get("/ai");
};

export const sparkUpdateSession = (id, contentStr) => {
  const content = JSON.stringify(contentStr)
  let data = {
    id,
    content
  }
  return request.put("/ai", data);
}