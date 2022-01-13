<template>
  <div class="form-container" v-if="pageShow">
    <div class="title">
      <div class="title-head">{{formObject.title}}</div>
      <div class="title-body">
        <div>
          {{formObject.content}}
        </div>
      </div>
    </div>
    <div class="form">
      <el-form
        ref="elForm"
        :model="formData"
        :rules="rules"
        size="medium"
        label-width="100px"
      >
        <el-form-item label="学校" prop="university">
          <el-select
            v-model="formData.university"
            placeholder="请选择学校"
            :disabled="true"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="(item, index) in universityOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍楼" prop="building">
          <el-select
            v-model="formData.building"
            @change="bindBuildingChange"
            placeholder="请选择宿舍楼"
            filterable
            clearable
            :style="{ width: '100%' }"

          >
            <el-option
              v-for="(item, index) in buildingOptions"
              :key="index"
              :label="item.buildingName"
              :value="item.buildingId"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="楼层" prop="floor">
            <el-select
            v-model="formData.floor"
            placeholder="请选择楼层"
            :disabled="false"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="(item, index) in floorOptions"
              :key="index"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房号" prop="roomNo">
          <el-input
            v-model="formData.roomNo"
            placeholder="请输入房号 （如 333）"
            clearable
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
        <el-form-item size="large">
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog
  title="兑奖码"
  :visible.sync="centerDialogVisible"
  width="80%"
  center>
  <div class="text" >
    <div>您的兑奖码为：{{code}}</div>
  <div>请到客服微信查询是否中奖</div>
  </div>
  <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
  </span>
</el-dialog>
  </div>
</template>
<script>
import {
  postDormitoryStatistics
} from '@/api/dormitory'
import { getUniversityById,getBuildingList } from '@/api/university'
import { getFormById } from '@/api/form'
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        university: undefined,
        building: undefined,
        buildingNp: undefined,
        floor: undefined,
        roomNo: undefined,
      },
      rules: {
        university: [
          {
            required: true,
            message: "请选择学校",
            trigger: "change",
          },
        ],
        building: [
          {
            required: true,
            message: "请选择宿舍楼",
            trigger: "change",
          },
        ],
        buildingNp: [
          {
            required: true,
            message: "请选择楼号",
            trigger: "change",
          },
        ],
        floor: [
          {
            required: true,
            message: "请输入楼层 （如 3层）",
            trigger: "blur",
          },
        ],
        roomNo: [
          {
            required: true,
            message: "请输入房号 （如 333）",
            trigger: "blur",
          },
        ],
      },
      universityId:null,

      universityOptions: [],
      buildingOptions: [],
      buildingNpOptions: [],
      floorOptions: [],
      centerDialogVisible: false,
      code:NaN,
      formObject: {},
      pageShow: false
    };
  },
  computed: {},
  watch: {},
  created() {
    this.universityId = this.$route.query.university;
    this.getInit()
    this.getUniversityName()
    this.getBuildingOptions()
  },
  mounted() {},
  methods: {
    getInit(){
      getFormById({"universityId": this.universityId}).then(res =>{
        if (res.data.status === 0){
          this.pageShow = true
        }
        this.formObject = res.data
      })

    },
    getUniversityName(){
      getUniversityById({universityId:this.universityId}).then(res=>{
        this.universityOptions.push(
          {
            "label": res.data.universityName,
            "value": res.data.universityId
          }
        )
        this.formData.university = res.data.universityName
      })
    },
    getBuildingOptions(){
      getBuildingList({
        "page": 1,
        "limit": 999,
        "universityId": this.universityId
      }).then(res=>{
        this.buildingOptions = res.data.items;
      })
    },
    bindBuildingChange(e){
      let temp = []
      for (const ele of this.buildingOptions) {
        if (e === ele.buildingId){
          for (let i = 1; i <= ele.floor; i++) {
            temp.push(i)
          }
          this.floorOptions = temp
        }
      }

    },
    submitForm() {
      this.$refs["elForm"].validate((valid) => {
        if (!valid) return;
        let data = new FormData()
        data.append("buildingName", this.formData.building)
        data.append("floor", this.formData.floor)
        data.append("roomNo", this.formData.roomNo)
        postDormitoryStatistics(data).then(response =>{
          this.$notify({
              title: '成功',
              message: '提交成功',
              type: 'success',
              duration: 2000
            })
            this.centerDialogVisible = true
            this.code = response.data
        })
      });
    },
    resetForm() {
      this.$refs["elForm"].resetFields();
    },

  },
};
</script>
<style lang="scss" scoped>
.form-container {
  // padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  //justify-content: center;
  //background-image: linear-gradient(120deg, #fdfbfb 0%, #ebedee 100%);
  height: 100%;
  .title {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .title-head {
      font-size: 24px;
      font-weight: bold;
      color: #333;
    }
    .title-body {
      margin-top: 10px;
      color: #363636;
      font-size: 14px;
      line-height: 20px;
      margin-bottom: 15px;
      width: 90%;
    }
  }

  .form {
    width: 90%;
    margin-top: 15px;
  }
  .text{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
}


</style>
