
import request from '@/utils/request'

export function getUniversityList() {
  return request({
    url: '/university/getUniversityList',
    method: 'get'
  })
}

export function addUniversity(data) {
  return request({
    url: '/university/addUniversity',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    data
  })
}

export function UpdateUniversityById(data) {
  return request({
    url: '/university/UpdateUniversityById',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    data
  })
}

export function deleteUniversity(data) {
  return request({
    url: '/university/deleteUniversity',
    method: 'get',
    params: data
  })
}

export function getBuildingList(data) {
  return request({
    url: '/building/getBuildingList',
    method: 'get',
    params: data
  })
}

export function deleteAllBuildings(data) {
  return request({
    url: '/building/deleteAllBuildings',
    method: 'get',
    params: data
  })
}

export function saveBuildingList(data) {
  return request({
    url: '/building/saveBuildingList',
    method: 'post',
    data
  })
}

export function getUniversityById(data) {
  return request({
    url: '/university/getUniversityById',
    method: 'get',
    params: data
  })
}
export function updateBuilding(data) {
  return request({
    url: '/building/updateBuilding',
    method: 'post',
    data
  })
}

