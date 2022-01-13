<template>
  <div class="app-container">
    <el-button
      class="filter-item"
      style="margin-bottom: 10px"
      type="primary"
      icon="el-icon-edit"
      @click="handleCreate"
    >
      {{ $t("table.add") }}
    </el-button>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column :label="tableCol.universityName" width="150px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.universityName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.title" width="120px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.createTime" width="140px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.status" width="100px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.status}}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.href" width="120px" align="center">
        <template slot-scope="{ row }">
          <span>
            <a @click="handleClipboard(baseFormUrl+row.id,$event)">点击复制链接</a>
          </span>
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
            {{ $t("table.edit") }}
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(row, $index)"
          >
            {{ $t("table.delete") }}
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

    <el-dialog :visible.sync="dialogFormVisible" :title="textMap[dialogStatus]"       width="100%">
      <el-form
        ref="dataForm"
        :model="temp"
        label-position="left"
        label-width="70px"
      >
        <el-form-item label="学校">
          <el-select
            v-model="temp.universityId"
            placeholder="所属学校"
            class="filter-item"
            :disabled="dialogStatus!='create'"
          >
            <el-option
              v-for="item in universityOption"
              :key="item.universityId"
              :label="item.universityName"
              :value="item.universityId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="temp.status"
            placeholder="请选择状态"
            class="filter-item"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="temp.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="文本">
          <el-input v-model="temp.content" placeholder="请输入文本内容" type="textarea" :rows="4"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t("table.cancel") }}
        </el-button>
        <el-button
          type="primary"
          @click="
            dialogStatus === 'create'? createData(): updateData()"
        >
          {{ $t("table.confirm") }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getFormList, addForm, updateForm, deleteForm} from '@/api/form'
import Pagination from "@/components/Pagination";
import { getUniversityList } from '@/api/university'
import clipboard from '@/utils/clipboard'
export default {
  name: "FormManage",
  components: { Pagination },
  data() {
    return {
      tableKey: 0,
      /**
       * 数据数组
       */
      list: null,
      /**
       * 提示框状态
       */
      dialogStatus: "",
      /**
       * 提示框显示
       */
      dialogFormVisible: false,

      statusOptions:[
        {
          value: 0,
          label: "正常"
        },
        {
          value: 1,
          label: "停用"
        }
      ],
      universityOption: [],
      textMap: {
        update: "编辑",
        create: "新建",
      },
      total: 0,
      /**
       * 加载图
       */
      listLoading: false,
      /**
       * 表头
       */
      tableCol: {
        universityName: "所属大学",
        title: "标题",
        createTime:"创建时间",
        status: "表单状态",
        href: "表单地址"
      },
      /**
       * 缓存数据
       */
      temp:{
        universityId: "",
        title: "",
        createTime:"",
        status: "",
        content: "",
      },
      /**
       * 查询数据
       */
      listQuery: {
        page: 1,
        limit: 40,

        sort: "+id",
      },
      baseFormUrl: window.location.href.replace(this.$route.path, "/dormitory_static_form?university=")

    };
  },
  created(){


    console.log(this.baseFormUrl)
    this.getList()
    this.getUniversityOption()
  },
  methods: {
    getList(){
      getFormList().then(res => {
        this.list = res.data
    })
    },

    getUniversityOption: function() {
      getUniversityList().then(res => {
        this.universityOption = res.data
        console.log(this.universityOption)
      })
    },
    resetTemp(){
      this.temp = {
        universityName: "",
          title: "",
          createTime:"",
          status: "",
          content: "",
      }
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.createTime = new Date(this.temp.createTime);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },
    handleDelete(row, index) {
      this.temp = Object.assign({}, row); // copy obj
      this.$confirm("是否确认删除","删除").then(res => {
        if (row.status === "正常"){
          row.status = 0
        }

        if (row.status === "停用"){
          row.status = 1
        }
        deleteForm(row).then(res=>{
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

        console.log(res)
      }).catch(err => {
        console.log(err)
      })
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },

    createData(){
      addForm(this.temp).then(res=>{
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

    updateData(){
      if (this.temp.status === "正常"){
        this.temp.status = 0
      }

      if (this.temp.status === "停用"){
        this.temp.status = 1
      }

      updateForm(this.temp).then(res=>{
        if (res.code === 20000) {
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
          this.$router.go(0)
        } else {
          this.$notify({
            title: '失败',
            message: '更新失败',
            type: 'fail',
            duration: 2000
          })
        }
      })
      console.log(this.temp)
    },
    handleClipboard(text,event){
      clipboard(text, event)
    }

  },
};
</script>

<style>
.show-data{
  font-size: 20px;
  color: #363636;
  margin-bottom: 10px;
}
</style>
