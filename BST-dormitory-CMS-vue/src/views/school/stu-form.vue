<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select
        v-model="listQuery.buildingsNo"
        placeholder="宿舍楼"
        style="width: 200px; margin-right: 10px"
        class="filter-item"
        @change="buildingsChange"
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
        @change="floorChange"
      >
        <el-option
          v-for="item in floorOption"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>

      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleSearch"
      >
        {{ $t("table.search") }}
      </el-button>
    </div>
    <div class="show-data">
      当前宿舍楼填表人数：{{buildingFormNum}}，当前楼层填表人数：{{floorFormNum}}
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
      <!-- <el-table-column :label="tableCol.university" width="110px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.university }}</span>
        </template>
      </el-table-column> -->
      <el-table-column :label="tableCol.buildingName" width="100px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.buildingName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.floor" width="70px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.floor }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.roomNo" width="70px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.roomNo }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="tableCol.counts" width="90px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.counts }}</span>
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
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getBuildingsByUniversityId,
  getDormitoryStatistics,
  getDormitoryStatisticsByBuildingNoAndFloor,
  getInputFormCount
} from "@/api/dormitory";
export default {
  name: "StuForm",
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
      listLoading: true,
      /**
       * 表头
       */
      tableCol: {
        university: "学校",
        buildingName: "宿舍楼",
        floor: "楼层",
        roomNo: "房号",
        counts: "填写人数"
      },
      /**
       * 查询数据
       */
      listQuery: {
        page: 1,
        limit: 40,
        buildingsNo: "",
        floor: "",
        sort: "+id",
      },
      /**
       * 宿舍楼选项
       */
      buildingsOption: [],
      floorOption:[],
      /**
       * 填表统计
       */
      buildingFormNum:'[未选择]',
      floorFormNum:'[未选择]',
      universityId: null
    };
  },
  created(){
    for(var key in this.$route.params){
      this.universityId = key;
    }
    this.getBuildings()
    this.getList()
  },
  methods: {
    getBuildings() {
      getBuildingsByUniversityId(this.universityId).then((response) => {
        this.buildingsOption = response.data;
      });
    },
    handleSearch() {
      this.listLoading = true
      var data = {
        buildingNo: this.listQuery.buildingsNo,
        floor: this.listQuery.floor
      }
      getDormitoryStatisticsByBuildingNoAndFloor(data).then(response => {
        this.list = response.data.items;
        this.total = response.data.total;
        this.listLoading = false;
      })

    },
    getList(){
      this.listLoading = true;
      getDormitoryStatistics({
        universityId: this.universityId,
        page: this.listQuery.page,
        limit: this.listQuery.limit
      }).then((response) => {
        this.list = response.data.items;
        this.total = response.data.total;

        this.listLoading = false;
      })
    },
    buildingsChange(e){
      // 情况数据
      this.buildingFormNum = 0
      this.floorFormNum = 0

      // 获取数据
      getInputFormCount({
        "buidingFormNum": this.listQuery.buildingsNo,
      }).then(response =>{
        // console.log(response)
        this.buildingFormNum = response.data.count
      })

      //获取楼层
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
    floorChange(){
      getInputFormCount({
        "buidingFormNum": this.listQuery.buildingsNo,
        "floor": this.listQuery.floor
      }).then(response =>{
        // console.log(response)
        this.floorFormNum = response.data.count
      })
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
