<template>
  <a-modal
    title="新增用户"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="用户名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['username', {rules: [{required: true, min: 2, message: '请输入至少两个字符的用户名称！'}]}]"/>
        </a-form-item>
        <a-form-item
          label="昵称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['nikename', {rules: [{required: true, min: 2, message: '请输入至少两个字符的用户名称！'}]}]"/>
        </a-form-item>
        <a-form-item
          label="密码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input type="password" v-decorator="['password', {rules: [{required: true, min: 6, message: '请输入至少6个字符！'}]}]"/>
        </a-form-item>
        <a-form-item
          label="角色列表"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select mode="multiple" @change="handleRoleChange" v-decorator="['roleIds', {rules: [{required: true, message: '请选择角色列表！'}]}]" placeholder="请选择角色列表">
            <a-select-option
              v-for="role in roles"
              :key="role.id+''"
            >{{ role.name }}
            </a-select-option
            >
          </a-select>
        </a-form-item>
        <a-form-item
          label="是否启用"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select
            v-decorator="['enabled', {rules: [{required: true, message: '请选择状态！'}]}]">
            <a-select-option value="1">是</a-select-option>
            <a-select-option value="0">否</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { createUser, getRoleList } from '@/api/manage'

export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,

      form: this.$form.createForm(this),
      roles: []
    }
  },
  created () {
    getRoleList().then((res) => {
      this.roles = res.result
    })
  },
  methods: {
    add () {
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          createUser({
            username: values.username,
            nikename: values.nikename,
            password: values.password,
            enabled: values.enabled === '1',
            roleIds: values.roleIds.join(',')
          }).then((res) => {
            this.confirmLoading = false
            if (res.success) {
              this.visible = false
              this.form.resetFields()
              this.$emit('ok', values)
            } else {
              this.$message.warning(res.message)
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
    },
    handleRoleChange (value) {
      console.log(`selected ${value}`)
    }
  }
}
</script>
