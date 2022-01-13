<template>
  <div class="app-container">
    <el-select
      v-model="listQuery.universityId"
      placeholder="请选择大学"
      style="width: 240px; margin-bottom: 20px"
      class="filter-item"
      @change="bindSelectQuery"
    >
      <el-option
        v-for="item in universityOption"
        :key="item.universityId"
        :label="item.universityName"
        :value="item.universityId"
      />
    </el-select>

    <div style="float: right">
      <el-button
        class="filter-item"
        style="margin-left: 10px"
        type="primary"
        icon="el-icon-edit"
        @click="handleUpload"
      >
        excel导入
      </el-button>
      <el-button
        class="filter-item"
        type="danger"
        icon="el-icon-delete"
        @click="handleClean"
      >
        清空
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      id="el-table"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column :label="tableCol.buildingId" prop="id" sortable="custom" align="center" width="80"
                       :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.buildingId }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.universityName" width="120px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.universityName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.buildingName" width="140px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.buildingName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.floor" width="140px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.floor }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.actions')"
        align="center"
        width="160"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            {{ $t('table.edit') }}
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <!--    编辑面板-->
    <el-dialog
      :title="textMap[dialogStatus]"
      width="100%"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :model="temp"
        label-position="left"
        label-width="70px"
        v-if="dialogStatus === 'delete' ? false : true"
      >
        <el-form-item label="id">
          <el-input v-model="temp.buildingId" placeholder="Id自动生成" :disabled="true"/>
        </el-form-item>

        <el-form-item label="大学">
          <el-select
            :disabled="true"
            v-model="temp.universityName"
            placeholder="请选择大学"
            class="filter-item"
          >
            <el-option
              v-for="item in universityOption"
              :key="item.universityId"
              :label="item.universityName"
              :value="item.universityId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍楼名">
          <el-input v-model="temp.buildingName" placeholder="输入宿舍楼名"/>
        </el-form-item>
        <el-form-item label="楼层">
          <el-input type="number" v-model="temp.floor" placeholder="输入楼层"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button
          type="primary"
          @click="updateData()"
        >
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog>
    <!--    清空提示-->
    <el-dialog
      title="提示"
      :visible.sync="deleteDialogVisible"
      width="100%">
      <span>即将清空当前院校的宿舍楼信息，是否确定</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="cleanData">确 定</el-button>
      </span>
    </el-dialog>
    <!--    excel导入-->
    <el-dialog
      title="从excel导入"
      :visible.sync="uploadDialogVisible"
      width="100%">
      <div style="color: #363636; text-align: center; margin-bottom: 20px">点击此处下载：
        <el-button @click="handleDownload" type="primary">标准导入模板</el-button>
      </div>

      <upload-excel-component :on-success="handleUploadExcelSuccess" :before-upload="beforeUpload" />
      <div style="color: #363636; text-align: left; margin: 20px">
        其中共有三个字段：<br>
        universityId为学的编号，该编号可以在【大学管理-学校管理】中查到 <br>
        buildingName为该大学的宿舍楼名，如（榕苑1号楼，榕苑2号楼）<br>
        floor为该宿舍楼的楼层，填写数字，如（1,2,3）<br>
        填写完后保存，拖拽到下方或选择文件即可在下方预览。确认无误后 确定 上传。
      </div>
      <el-button v-show="showSave" style="float: right; margin-bottom: 10px" @click="handleSave" type="primary">保存</el-button>
      <el-table :data="excelTableData" border highlight-current-row style="width: 100%;margin-top:20px;">
        <el-table-column v-for="item of excelTableHeader" :key="item" :prop="item" :label="item" />
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import {
  getUniversityList,
  getBuildingList,
  deleteAllBuildings,
  saveBuildingList,
  updateBuilding
} from '@/api/university'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import UploadExcelComponent from '@/components/UploadExcel/index.vue'
export default {
  name: 'BuildingManage',
  components: { Pagination, UploadExcelComponent },
  data() {
    return {
      listQuery: {
        universityId: '',
        page: 1,
        limit: 40
      },
      tableCol: {
        buildingId: 'id',
        universityName: '所属院校',
        buildingName: '宿舍楼名',
        floor: '楼层'
      },
      universityOption: [],
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: false,
      dialogFormVisible: false,
      deleteDialogVisible: false,
      uploadDialogVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新建'
      },
      temp: {
        buildingId: '',
        universityName: '',
        buildingName: '',
        floor: ''
      },
      excelTableData: [],
      excelTableHeader: [],
      showSave: false
    }
  },
  created() {
    this.getUniversityOption()
  },
  methods: {
    getUniversityOption: function() {
      getUniversityList().then(res => {
        this.universityOption = res.data
        console.log(this.universityOption)
      })
    },

    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },

    bindSelectQuery: function() {
      this.getList()
      // console.log(this.listQuery)
    },

    getList: function() {
      getBuildingList(this.listQuery).then(res => {
        this.list = res.data.items
        this.total = res.data.total
      })
    },

    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleClean() {
      if (this.listQuery.universityId === '') {
        this.$message({
          message: "未选择院校",
          type: "warning"
        })
      } else {
        this.deleteDialogVisible = true
      }
    },

    handleSave(){
      saveBuildingList({ "excelTableData": this.excelTableData }).then(res=>{
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
            this.$router.go(0)
          } else {
            this.$notify({
              title: '失败',
              message: '添加失败',
              type: 'fail',
              duration: 2000
            })
          }
      })

    },
    handleUpload() {
      this.uploadDialogVisible = true
    },
    cleanData() {
      deleteAllBuildings(this.temp).then(res => {
        if (res.code === 20000) {
          this.$notify({
            title: '成功',
            message: '清空成功',
            type: 'success',
            duration: 2000
          })
          this.$router.go(0)
        } else {
          this.$notify({
            title: '失败',
            message: '清空失败',
            type: 'fail',
            duration: 2000
          })
        }
      })
      this.deleteDialogVisible = false

    },
    resetTemp: function() {
      this.temp = {
        buildingId: '',
        universityName: '',
        buildingName: ''
      }
    },

    handleUploadExcelSuccess({results, header}){
      this.excelTableData = results
      this.excelTableHeader = header

      this.showSave = true
    },

    beforeUpload(file){
      const isLt5M = file.size / 1024 / 1024 < 5

      if (isLt5M){
        return true
      }

      this.$message({
        message: "上传的文件请勿超过5m",
        type: "warning"
      })
    },

    updateData(){
      updateBuilding(this.temp).then(res =>{
        if (res.code === 20000) {
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success',
            duration: 2000
          })
          this.$router.go(0)
        } else {
          this.$notify({
            title: '失败',
            message: '添加失败',
            type: 'fail',
            duration: 2000
          })
        }
      })
    },
    handleDownload(){
      import("@/vendor/Export2Excel").then((excel) => {
        const tHeader = [
          "universityId",
          "buildingName",
          "floor"
        ]
        const data = []
        excel.export_json_to_excel({header: tHeader,data,})
      })
    }
  }
}
</script>
<style>

</style>
