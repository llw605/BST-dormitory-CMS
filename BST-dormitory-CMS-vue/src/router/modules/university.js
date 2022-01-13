import Layout from '@/layout'
import {
  getUniversityList
} from '@/api/university'

let schoolRouter = []

getUniversityList().then(res => {

  res.data.forEach(item => {
    let router = {
      path: 'school' + item.universityId,
      component: () => import('@/views/school/index'),
      name: item.universityId,
      meta: {
        title: item.universityName,
        roles: ['admin', item.role]
      },
      children: [{
          path: 'dormitory-info/:' + item.universityId,
          component: () => import('@/views/school/dormitory-info'),
          name: 'DormitoryInfo',
          meta: {
            title: '宿舍信息记录'
          }
        },
        {
          path: 'StuForm/:' + item.universityId,
          component: () => import('@/views/school/stu-form'),
          name: 'StuForm',
          meta: {
            title: '学生表单信息'
          }
        },
      ]
    }
    schoolRouter.push(router)

  });

})


const universityRouter = {
  path: '/university',
  component: Layout,
  name: 'University',
  meta: {
    title: '学校列表',
    icon: 'el-icon-school'
  },
  children: schoolRouter
}

export default universityRouter
