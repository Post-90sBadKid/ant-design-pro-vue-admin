import request from '@/utils/request'

const api = {
  user: '/user',
  role: '/role',
  service: '/service',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  permissionTree: '/permission/tree',
  orgTree: '/org/tree'
}

export default api

export function getUserList (parameter) {
  return request({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function getRoleList (parameter) {
  return request({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList (parameter) {
  return request({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions (parameter) {
  return request({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

export function getPermissionTree () {
  return request({
    url: api.permissionTree,
    method: 'get'
  })
}

export function getOrgTree (parameter) {
  return request({
    url: api.orgTree,
    method: 'get',
    params: parameter
  })
}

export function deleteRole (parameter) {
  return request({
    method: 'DELETE',
    url: api.role + '/' + parameter
  })
}

export function saveRole (data) {
  return request({
    url: api.role,
    method: data.id ? 'put' : 'post',
    data: data
  })
}

export function deleteUser (parameter) {
  return request({
    url: api.user,
    method: 'delete',
    data: parameter
  })
}

export function updateUser (parameter) {
  return request({
    url: api.user,
    method: 'put',
    data: parameter
  })
}

export function createUser (parameter) {
  return request({
    url: api.user,
    method: 'post',
    data: parameter
  })
}

export function deletePermission (parameter) {
  return request({
    url: api.permission + '/' + parameter,
    method: 'delete'
  })
}

export function updatePermission (data) {
  return request({
    url: api.permission,
    method: 'put',
    data: data
  })
}

export function createPermission (data) {
  return request({
    url: api.permission,
    method: 'post',
    data: data
  })
}
// id == 0 add     post
// id != 0 update  put
export function saveService (parameter) {
  return request({
    url: api.service,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function saveSub (sub) {
  return request({
    url: '/sub',
    method: sub.id === 0 ? 'post' : 'put',
    data: sub
  })
}
