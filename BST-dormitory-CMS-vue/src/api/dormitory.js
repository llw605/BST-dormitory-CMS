import request from '@/utils/request'

/**
 * 获取 宿舍信息表
 * @param {*} query
 * @returns
 */
export function fetchList(query) {
  return request({
    url: '/dormitory/fetchList',
    method: 'get',
    params: query
  })
}

/**
 * 根据大学id获取宿舍楼名
 * @param {*}} universityId
 * @returns
 */
export function getBuildingsByUniversityId(universityId) {
  return request({
    url: '/building/getBuildingsByUniversityId',
    method: 'get',
    params: {
      'universityId': universityId
    }
  })
}

/**
 * 根据大学id和宿舍获取宿舍楼
 * @param {*} universityId
 * @param {*} name
 * @returns
 */
export function getBuildingsByUniversityIdAndName(universityId, name) {
  return request({
    url: '/building/getBuildingsByUniversityIdAndName',
    method: 'get',
    params: {
      'universityId': universityId,
      'name': name
    }
  })
}

/**
 * 根据宿舍楼id以及楼层获取宿舍
 * @param {*} data
 * @returns
 */
export function getDormitoryInfoByBuildingNoAndFloor(data) {
  return request({
    url: '/dormitory/getDormitoryInfoByBuildingNoAndFloor',
    method: 'get',
    params: data
  })
}

export function getDormitoryStatisticsByBuildingNoAndFloor(data){
  return request({
    url: '/dormitory/getDormitoryStatisticsByBuildingNoAndFloor',
    method: 'get',
    params: data
  })
}

/**
 * 更新宿舍信息
 * @param {*}} data
 * @returns
 */
export function updateDormitoryInfo(data) {
  return request({
    url: '/dormitory/updateDormitoryInfo',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    data
  })
}

/**
 * 上传宿舍信息统计表单
 * @param {*} data
 * @returns
 */
export function postDormitoryStatistics(data){
  return request({
    url: '/dormitory/postDormitoryStatistics',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    data
  })
}

/**
 * 创建宿舍信息
 * @param {*} data
 * @returns
 */
export function createDormitoryInfo(data){
  return request({
    url: '/dormitory/createDormitoryInfo',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    data
  })
}

/**
 * 获取宿舍表单信息
 * @param {*} query
 * @returns
 */
export function getDormitoryStatistics(query) {
  return request({
    url: '/dormitory/getDormitoryStatistics',
    method: 'get',
    params: query
  })
}

/**
 * 获取表单提交数量
 * @param {*} query
 * @returns
 */
export function getInputFormCount(query) {
  return request({
    url: '/dormitory/getInputFormCount',
    method: 'get',
    params: query
  })
}

export function saveDormitoryList(data) {
  return request({
    url: '/dormitory/saveDormitoryList',
    method: 'post',
    data
  })
}
