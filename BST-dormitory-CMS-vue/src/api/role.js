import request from '@/utils/request'

export function getUserList(data) {
  return request({
    url: '/user/adminGetUser',
    method: 'get',
    params: data
  })
}

export function getRolesList() {
  return request({
    url: '/user/getRolesList',
    method: 'get', 
  })
}

export function addUser(data) {
  return request({
    url: '/user/addUser',
    method: 'post', 
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/updateUser',
    method: 'post', 
    data
  })
}

export function resetPassword(data) {
  return request({
    url: '/user/resetPassword',
    method: 'post', 
    data
  })
}



