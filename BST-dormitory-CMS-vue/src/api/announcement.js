import request from '@/utils/request'

export function addNewAnnotation(data) {
  return request({
    url: '/annotation/addNewAnnotation',
    method: 'post',
    headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},
    data
  })
}


export function getAnnotationList(){
  return request({
    url: '/annotation/getAnnotationList',
    method: 'get'
  })
}