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
      :key="0"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column :label="tableCol.userName" width="100px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.nickName" width="100px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.nickName }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="tableCol.role" width="90px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.role }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="tableCol.status" width="80px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.status == 0 ? "正常" : "停用" }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.password" width="100px" align="center">
        <template slot-scope="{ row }">
          <span
            @click="handleResetPassword(row)"
            style="color: #f56c6c; text-decoration: underline"
            >修改密码</span
          >
        </template>
      </el-table-column>
      <el-table-column
        :label="tableCol.phonenumber"
        width="100px"
        align="center"
      >
        <template slot-scope="{ row }">
          <span>{{ row.phonenumber }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="tableCol.createTime"
        width="100px"
        align="center"
      >
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
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
            :disabled="true"
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
        :model="temp"
        label-position="left"
        label-width="70px"
      >
        <el-form-item
          label="用户名"
          v-if="dialogStatus === 'create' ? true : false"
        >
          <el-input v-model="temp.userName" placeholder="登入账号" />
        </el-form-item>
        <el-form-item
          label="昵称"
          v-if="dialogStatus === 'restPassword' ? false : true"
        >
          <el-input v-model="temp.nickName" placeholder="昵称" />
        </el-form-item>
        <el-form-item
          label="手机号"
          v-if="dialogStatus === 'restPassword' ? false : true"
        >
          <el-input v-model="temp.phonenumber" placeholder="联系电话" />
        </el-form-item>

        <el-form-item
          label="权限"
          v-if="dialogStatus === 'restPassword' ? false : true"
        >
          <el-select
            v-model="temp.userRole"
            class="filter-item"
            placeholder="请选择账号权限"
          >
            <el-option
              v-for="item in roleOptions"
              :key="item.id"
              :label="item.role"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            :disabled="dialogStatus === 'update' ? true : false"
            v-model="temp.password"
            placeholder="请输入密码"
          />
        </el-form-item>
        <el-form-item
          label="账号状态"
          v-if="dialogStatus === 'restPassword' ? false : true"
        >
          <el-select
            v-model="temp.status"
            class="filter-item"
            placeholder="账号状态"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
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
              : resetPasswordData()
          "
        >
          {{ $t("table.confirm") }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getUserList,
  getRolesList,
  addUser,
  updateUser,
  resetPassword,
} from "@/api/role";
export default {
  name: "Manage",
  components: { Pagination },
  data() {
    return {
      listLoading: false,
      list: null,
      total: 0,
      tableCol: {
        userName: "用户名",
        nickName: "昵称",
        password: "密码",
        userRole: "权限",
        status: "账号状态",
        createTime: "创建时间",
        phonenumber: "手机号",
      },
      listQuery: {
        page: 1,
        limit: 40,
        buildingsNo: "",
        floor: "",
        sort: "+id",
      },
      textMap: {
        update: "编辑",
        create: "新建",
      },
      temp: {
        userName: "",
        nickName: "",
        password: "",
        userRole: "",
        status: "",
        createTime: "",
        phonenumber: "",
      },
      dialogFormVisible: false,
      dialogStatus: "",
      roleOptions: [],
      statusOptions: [
        {
          label: "正常",
          value: 0,
        },
        {
          label: "停用",
          value: 1,
        },
      ],
    };
  },
  mounted() {
    this.getList();
    this.getRolesList();
  },
  methods: {
    handleDelete(row, index) {
      this.$notify({
        title: "成功",
        message: "删除成功",
        type: "success",
        duration: 2000,
      });
      this.list.splice(index, 1);
    },
    handleResetPassword(row, index) {
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = "restPassword";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    getList() {
      this.listLoading = true;
      getUserList(this.listQuery).then((response) => {
        this.total = response.data.total;
        this.list = response.data.items;

        this.listLoading = false;
      });
    },
    getRolesList() {
      getRolesList().then((response) => {
        this.roleOptions = response.data;
      });
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    resetTemp() {
      this.temp = {
        userName: "",
        nickName: "",
        password: "",
        role: "",
        status: "",
        createTime: "",
        phonenumber: "",
      };
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
      let formData = new FormData();
      formData.append("userName", this.temp.userName);
      formData.append("nickName", this.temp.nickName);
      formData.append("phonenumber", this.temp.phonenumber);
      formData.append("userRole", this.temp.userRole);
      formData.append("status", this.temp.status);
      updateUser(formData)
        .then((response) => {
          if (response.code === 20000) {
            this.$notify({
              title: "成功",
              message: "更新成功",
              type: "success",
              duration: 2000,
            });
          } else {
            this.$notify({
              title: "失败",
              message: "更新失败",
              type: "fail",
              duration: 2000,
            });
          }
        })
        .catch((err) => {
          this.$notify({
            title: "失败",
            message: err,
            type: "fail",
            duration: 2000,
          });
        });
      this.dialogFormVisible = false;
    },
    resetPasswordData() {
      let formData = new FormData();
      formData.append("userName", this.temp.userName);
      formData.append("password", this.temp.password);

      resetPassword(formData)
        .then((response) => {
          if (response.code === 20000) {
            this.$notify({
              title: "成功",
              message: "更新成功",
              type: "success",
              duration: 2000,
            });
          } else {
            this.$notify({
              title: "失败",
              message: "更新失败",
              type: "fail",
              duration: 2000,
            });
          }
        })
        .catch((err) => {
          this.$notify({
            title: "失败",
            message: err,
            type: "fail",
            duration: 2000,
          });
        });
      this.dialogFormVisible = false;
    },
    createData() {
      let formData = new FormData();
      formData.append("userName", this.temp.userName);
      formData.append("nickName", this.temp.nickName);
      formData.append("phonenumber", this.temp.phonenumber);
      formData.append("userRole", this.temp.userRole);
      formData.append("password", this.temp.password);
      formData.append("status", this.temp.status);
      addUser(formData)
        .then((response) => {
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
        })
        .catch((err) => {
          this.$notify({
            title: "失败",
            message: err,
            type: "fail",
            duration: 2000,
          });
        });
      this.dialogFormVisible = false;
    },
  },
};
</script>

<style>
</style>