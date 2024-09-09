import request from "@/utils/request";
export const sparkAddSession = (session1) => {
  let session = {
    content: session1.content,
  }
  console.log(session)
  return request.post("/ai", {
    session,
    headers: {
      "Content-Type": "application/json",
    },
  });
};
