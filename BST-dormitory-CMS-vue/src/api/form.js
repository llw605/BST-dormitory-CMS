import request from '@/utils/request'

export function getFormList() {
  return request({
    url: '/form/getFormList',
    method: 'get'
  })
}

export function addForm(data) {
  return request({
    url: '/form/addForm',
    method: 'post',
    data
  })
}

export function updateForm(data) {
  return request({
    url: '/form/updateForm',
    method: 'post',
    data
  })
}

export function deleteForm(data) {
  return request({
    url: '/form/deleteForm',
    method: 'post',
    data
  })
}

export function getFormById(data) {
  return request({
    url: '/form/getFormById',
    method: 'get',
    params: data
  })
}


