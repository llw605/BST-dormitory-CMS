<template>
  <div class="index-container">
    <el-row :gutter="20">
      <el-col :span="12" :xs="24">
        <el-card style="margin-bottom: 20px">
          <div slot="header" class="clearfix">
            <span>快捷导航</span>
          </div>
          <div class="nav">
            <el-tag v-permission="['admin','fushida']" hit effect="light" size="medium" @click="routeToDoInfo()">宿舍信息记录</el-tag>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" :xs="24">
        <el-card style="margin-bottom: 20px">
          <div slot="header" class="clearfix">
            <span>公告</span>
            <span style="float: right; font-size: 12px">{{
              annoList[0].uploadTime
            }}</span>
          </div>
          <div class="editor-content" v-html="annoList[0].content" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getAnnotationList } from "@/api/announcement";
export default {
  name: "Index",
  data() {
    return {
      annoList: [],
      annoCounts: "",
    };
  },
  mounted() {
    this.getAnnotation();

  },
  methods: {
    getAnnotation() {
      getAnnotationList().then((response) => {
        console.log(response);
        this.annoList = response.data;
        this.annoCounts = response.data.length;
      });
    },
    routeToDoInfo(){
      this.$router.push('/university/school/dormitory-info')
    },
  },
};
</script>

<style lang="scss" scoped>
.index-container {
  height: 100%;
  padding: 10px;
  // background-color: rgb(240, 242, 245);
  position: relative;
}

.box-center {
  margin: 0 auto;
  display: table;
}

.text-muted {
  color: #777;
}
</style>