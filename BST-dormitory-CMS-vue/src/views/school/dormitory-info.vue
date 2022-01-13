<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select
        v-model="listQuery.buildingsNo"
        placeholder="宿舍楼"
        style="width: 200px; margin-right: 10px"
        class="filter-item"
        @change="bindBuildingChange"
      >
        <el-option
          v-for="item in buildingsOption"
          :key="item.buildingId"
          :label="item.buildingName"
          :value="item.buildingId"
        />
      </el-select>

      <el-select
        v-model="listQuery.floor"
        placeholder="楼层"
        style="width: 90px; margin-right: 10px"
        class="filter-item"
      >
        <el-option
          v-for="item in floorOption"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>

      <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleSearch"
      >
        {{ $t("table.search") }}
      </el-button>

      <div style="float: right">
        <el-button
          class="filter-item"
          type="primary"
          icon="el-icon-upload"
          @click="handleUpload"
        >
          excel快捷导入
        </el-button>
        <el-button
          class="filter-item"
          style="margin-left: 10px"
          type="primary"
          icon="el-icon-edit"
          @click="handleCreate"
        >
          {{ $t("table.add") }}
        </el-button>
        <el-button
          v-waves
          :loading="downloadLoading"
          class="filter-item"
          type="primary"
          icon="el-icon-download"
          @click="handleDownload"
        >
          {{ $t("table.export") }}
        </el-button>
      </div>
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
      @sort-change="sortChange"
      @row-click="handleUpdate(row)"
    >
      <!-- <el-table-column :label="tableCol.id" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column> -->
      <el-table-column :label="tableCol.buildingNo" width="90px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.buildingNo }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.floor" width="50px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.floor }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.roomNo" width="60px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.roomNo }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column :label="tableCol.sex" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.sex==0?"女":row.sex==1?"男":"未知" }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        :label="tableCol.fansNo"
        class-name="status-col"
        width="100"
      >
        <template slot-scope="{ row }">
          <span>{{ row.fansNo }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="tableCol.toFansDiff"
        class-name="status-col"
        width="100"
      >
        <template slot-scope="{ row }">
          <span>{{ row.toFansDiff }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.comingTime" width="80px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.comingTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.visitors" width="140px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.visitors }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.remarks" width="190px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.remarks }}</span>
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
            v-if="row.status != 'deleted'"
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

    <el-dialog
      :title="textMap[dialogStatus]"
      width="100%"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
      >
        <el-form-item label="宿舍楼">
          <el-select
            :disabled="dialogStatus === 'create' ? false : true"
            v-model="temp.buildingNo"
            class="filter-item"
            placeholder="请选择宿舍楼"
          >
            <el-option
              v-for="item in buildingsOption"
              :key="item.buildingId"
              :label="item.buildingName"
              :value="item.buildingId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楼层">
          <el-input
            :disabled="dialogStatus === 'create' ? false : true"
            v-model="temp.floor"
            placeholder="请输入楼层  如（1层）"
          />
        </el-form-item>
        <el-form-item label="房号">
          <el-input
            :disabled="dialogStatus === 'create' ? false : true"
            v-model="temp.roomNo"
            placeholder="请输入房号 如（333）"
          />
        </el-form-item>
        <!-- <el-form-item label="性别">
          <el-select v-model="temp.sex" class="filter-item" placeholder="请选择性别">
            <el-option v-for="item in sexOption" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="加粉人数">
          <el-input
            v-model="temp.fansNo"
            placeholder="请输入添加人数  如（1）"
          />
        </el-form-item>
        <el-form-item label="加粉难度">
          <el-select
            v-model="temp.toFansDiff"
            class="filter-item"
            placeholder="默认一般"
          >
            <el-option
              v-for="item in DiffOption"
              :key="item"
              :label="item == 1 ? '简单' : item == 2 ? '一般' : '困难'"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="到访次数">
          <el-input
            disabled="true"
            v-model="temp.comingTime"
            placeholder="不用输入，编辑后自动递增"
          />
        </el-form-item>
        <el-form-item label="到访人员">
          <el-input v-model="temp.visitors" placeholder="请输入你的姓名 " />
        </el-form-item>
        <el-form-item label="备注信息">
          <el-input
            v-model="temp.remarks"
            type="textarea"
            placeholder="有备注信息吗？"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t("table.cancel") }}
        </el-button>
        <el-button
          type="primary"
          @click="dialogStatus === 'create' ? createData() : updateData()"
        >
          {{ $t("table.confirm") }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table
        :data="pvData"
        border
        fit
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{
          $t("table.confirm")
        }}</el-button>
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
        building_id为宿舍楼编号，该编号可以在【大学管理-宿舍楼管理】中查到 <br>
        floor为楼层，填写如（1层，2层，3层）<br>
        roomNo为当前楼层的房号，填写数字，如（101,102,103）<br>
        该表格填写较为繁琐以及困难，可以联系作者需要制作快速脚本。<br>
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
  fetchList,
  getBuildingsByUniversityId,
  getDormitoryInfoByBuildingNoAndFloor,
  updateDormitoryInfo,
  createDormitoryInfo,
  saveDormitoryList
} from '@/api/dormitory'
import waves from "@/directive/waves"; // waves directive
import { parseTime } from "@/utils";
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination
import UploadExcelComponent from '@/components/UploadExcel/index.vue'
import { saveBuildingList } from '@/api/university'

export default {
  name: "DormitoryInfo",
  components: { Pagination,UploadExcelComponent },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: "success",
        draft: "info",
        deleted: "danger",
      };
      return statusMap[status];
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type];
    },
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      tableCol: {
        id: "id",
        buildingNo: "宿舍楼",
        floor: "楼层",
        roomNo: "房号",
        sex: "性别",
        fansNo: "加粉人数",
        toFansDiff: "加粉难度",
        comingTime: "到访次数",
        visitors: "到访人员",
        remarks: "备注信息",
      },
      temp: {
        buildingId: "",
        floor: "",
        roomNo: "",
        fansNo: "",
        sex: "",
        toFansDiff: "",
        comingTime: "",
        // TODO:从缓存中获取用户名
        visitors: "",
        remarks: "",
      },
      listQuery: {
        page: 1,
        limit: 40,
        buildingsNo: "",
        floor: "",
        sort: "+id",
      },
      universityId: null,
      buildingsOption: [],
      floorOption:[],
      sexOption: ["男", "女", "未知"],
      DiffOption: [1, 2, 3],
      sortOptions: [
        { label: "ID Ascending", key: "+id" },
        { label: "ID Descending", key: "-id" },
      ],
      showReviewer: false,
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "新建",
      },
      dialogPvVisible: false,
      pvData: [],
      uploadDialogVisible: false,
      rules: {
        type: [
          { required: true, message: "type is required", trigger: "change" },
        ],
        timestamp: [
          {
            type: "date",
            required: true,
            message: "timestamp is required",
            trigger: "change",
          },
        ],
        title: [
          { required: true, message: "title is required", trigger: "blur" },
        ],
      },
      downloadLoading: false,
      excelTableData:[],
      excelTableHeader:[],
      showSave: false
    };
  },
  created() {
    for(var key in this.$route.params){
      this.universityId = key;
    }
    this.getList();
    this.getBuildings();
  },
  methods: {
    getList() {
      this.listLoading = true;
      fetchList({
        universityId: this.universityId,
        page: this.listQuery.page,
        limit: this.listQuery.limit
      }).then((response) => {
        this.list = response.data.items;
        this.total = response.data.total;

        this.listLoading = false;
      });
    },
    getBuildings() {
      getBuildingsByUniversityId(this.universityId).then((response) => {
        this.buildingsOption = response.data;
      });
    },
    handleSearch() {
      this.listLoading = true;
      var data = {
        buildingNo: this.listQuery.buildingsNo,
        floor: this.listQuery.floor,
      };
      getDormitoryInfoByBuildingNoAndFloor(data).then((response) => {
        this.list = response.data.items;
        this.total = response.data.total;
        this.listLoading = false;
      });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: "操作成功",
        type: "success",
      });
      row.status = status;
    },
    sortChange(data) {
      const { prop, order } = data;
      if (prop === "id") {
        this.sortByID(order);
      }
    },
    sortByID(order) {
      if (order === "ascending") {
        this.listQuery.sort = "+id";
      } else {
        this.listQuery.sort = "-id";
      }
      this.handleFilter();
    },
    resetTemp() {
      this.temp = {
        buildingNo: "",
        floor: "",
        roomNo: "",
        sex: "",
        toFansDiff: "一般",
        comingTime: "",
        visitors: "",
        remarks: "",
      };
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          let data = new FormData();
          data.append("buildingId", this.temp.buildingNo);
          data.append("floor", this.temp.floor);
          data.append("roomNo", this.temp.roomNo);

          data.append("fansNo", this.temp.fansNo);
          data.append("toFansDiff", this.temp.toFansDiff);
          data.append("visitors", this.temp.visitors);
          data.append("remarks", this.temp.remarks);
          console.log(data.getAll);
          createDormitoryInfo(data).then((response) => {
            if (response.code === 20000) {
              this.$notify({
                title: "成功",
                message: "创建成功",
                type: "success",
                duration: 2000,
              });
            } else {
              this.$notify({
                title: "失败",
                message: "创建失败",
                type: "fail",
                duration: 2000,
              });
            }
          });
        }
      });
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          let data = new FormData();
          data.append("dormitoryId", this.temp.id);
          data.append("fansNo", this.temp.fansNo);
          data.append("toFansDiff", this.temp.toFansDiff);
          data.append("visitors", this.temp.visitors);
          data.append("remarks", this.temp.remarks);
          console.log(data.getAll);
          updateDormitoryInfo(data).then((response) => {
            if (response.code === 20000) {
              this.$notify({
                title: "成功",
                message: "修改成功",
                type: "success",
                duration: 2000,
              });
            } else {
              this.$notify({
                title: "失败",
                message: "编辑失败",
                type: "fail",
                duration: 2000,
              });
            }
          });
        }
      });
    },
    bindBuildingChange(e){
      let temp = []
      for (const ele of this.buildingsOption) {
        if (e === ele.buildingId){
          for (let i = 1; i <= ele.floor; i++) {
            temp.push(i)
          }
          this.floorOption = temp
        }
      }
    },
    handleDelete(row, index) {
      this.$notify({
        title: "成功",
        message: "删除成功",
        type: "success",
        duration: 2000,
      });
      this.list.splice(index, 1);
    },
    getSortClass: function (key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? "ascending" : "descending";
    },
    handleDownload() {
      import("@/vendor/Export2Excel").then((excel) => {
        const tHeader = [
          "buildingId",
          "floor",
          "roomNo"
        ]
        const data = []
        excel.export_json_to_excel({header: tHeader,data,})
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          if (j === "timestamp") {
            return parseTime(v[j]);
          } else {
            return v[j];
          }
        })
      );
    },
    handleUpload() {
      this.uploadDialogVisible = true
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
    handleSave(){
      saveDormitoryList({ "excelTableData": this.excelTableData }).then(res=>{
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


  },
};
</script>

<style>
</style>
