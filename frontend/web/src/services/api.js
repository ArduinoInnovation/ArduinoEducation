import request from "../utils/request"

export function submitCode (params) {
  return request(`/api/v1/command/task/`,{
    method:'POST',
    body:params
  })
}

