<template>
  <div class="app-container">
    <div style="float: left">
      <el-button
        class="filter-item"
        style="margin-bottom: 10px"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >
        {{ $t("table.add") }}
      </el-button>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column
        :label="tableCol.id"
        prop="id"
        sortable="custom"
        align="center"
        width="80"
        :class-name="getSortClass('id')"
      >
        <template slot-scope="{ row }">
          <span>{{ row.universityId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="tableCol.universityName"
        width="200px"
        align="center"
      >
        <template slot-scope="{ row }">
          <span>{{ row.universityName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.role" width="120px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.role }}</span>
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
        v-if="dialogStatus === 'delete' ? false : true"
      >
        <el-form-item label="校名">
          <el-input v-model="temp.universityName" placeholder="输入学校名称" />
        </el-form-item>
        <el-form-item label="权限名">
          <el-input
            v-model="temp.role"
            :disabled="dialogStatus === 'create' ? false : true"
            placeholder="该校账号所对应的权限名"
          />
        </el-form-item>
      </el-form>
      <div
        v-show="dialogStatus === 'delete' ? true : false"
        style="font-size: 18px; text-align: center; color: #363636"
      >
        删除学校将连带删除含有该校权限的账号信息，宿舍信息将缓存至服务器一个月，是否确认？
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t("table.cancel") }}
        </el-button>
        <el-button
          type="primary"
          @click="
            dialogStatus === 'create'
              ? createData()
              : dialogStatus === 'update'
              ? updateData()
              : deleteData()
          "
        >
          {{ $t("table.confirm") }}
        </el-button>
      </div>
    </el-dialog>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  getUniversityList,
  addUniversity,
  UpdateUniversityById,
  deleteUniversity,
} from "@/api/university";
import Pagination from "@/components/Pagination";
export default {
  name: "University",
  components: { Pagination },
  data() {
    return {
      tableKey: 0,
      /**
       * 数据数组
       */
      list: null,
      total: 0,
      /**
       * 加载图
       */
      listLoading: false,
      /**
       * 表头
       */
      tableCol: {
        id: "id",
        universityName: "学校名称",
        role: "所属权限",
      },
      /**
       * 查询数据
       */
      listQuery: {
        page: 1,
        limit: 40,
        sort: "+id",
      },
      temp: {
        universityId: "",
        universityName: "",
        role: "",
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "新建",
      },
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
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      getUniversityList().then((res) => {
        this.list = res.data;
      });
    },
    getSortClass: function (key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? "ascending" : "descending";
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
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
    resetTemp() {
      this.temp = {
        universityId: "",
        universityName: "",
        role: "",
      };
    },
    createData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          let data = new FormData();
          data.append("universityName", this.temp.universityName);
          data.append("role", this.temp.role);

          addUniversity(data).then((res) => {
            if (res.code === 20000) {
              this.$notify({
                title: "成功",
                message: "添加成功",
                type: "success",
                duration: 2000,
              });
            } else {
              this.$notify({
                title: "失败",
                message: "添加失败",
                type: "fail",
                duration: 2000,
              });
            }
          });
          this.dialogFormVisible = false;
          this.getList();
        }
      });
    },

    updateData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          let data = new FormData();
          data.append("universityId", this.temp.universityId);
          data.append("universityName", this.temp.universityName);
          data.append("role", this.temp.role);

          UpdateUniversityById(data).then((res) => {
            if (res.code === 20000) {
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
          this.dialogFormVisible = false;
          this.getList();
        }
      });
    },
    handleDelete(row, index) {
      this.dialogStatus = "delete";
      this.dialogFormVisible = true;
      this.temp = Object.assign({}, row); // copy obj
    },
    deleteData() {
      // console.log(this.temp)
      deleteUniversity({ "universityId": this.temp.universityId }).then((res) => {
        if (res.code === 20000) {
          this.$notify({
            title: "成功",
            message: "删除成功",
            type: "success",
            duration: 2000,
          });
        } else {
          this.$notify({
            title: "失败",
            message: "删除失败",
            type: "fail",
            duration: 2000,
          });
        }
      });
      this.dialogFormVisible = false;
      this.getList();
    },
  },
};
</script>

<style>
.show-data {
  font-size: 20px;
  color: #363636;
  margin-bottom: 10px;
}
</style>
