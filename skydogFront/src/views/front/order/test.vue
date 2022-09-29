<template>
    <div>
        <form name="punchout_form" method="post" action="https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=fzzdYSdpD0sQndtjtRPVqrvV4DtK%2FkmItfakKjFOXUTDxW%2B8N6TS64A3Wj2vRGT4I8c1fHgYAR0MhGGWwg2ILFjml5A70GrLgCQZ6hW6RNLzMDatrDl2Un0fecGQE7Wsdu5LWeCrsvNnt6TdGhf5Ydj%2BCPFXTMzNFoTa%2FTqKcCjzF30Pw7bfWRyQa5SO09ShWEeQQMaQeqgg7pIGCaV%2Bza2kFSL1vUGfcbfd6jX6KaGLy%2FIKHEbqwZhvHO6LEdb%2Fe0MADLUgIkQ990Sh7QObFP9MFzm8mRgKWRB6e2WvQbZSWLyklAVwMe%2FCS15bn%2FRH5boVOA02616CPJeRXv%2BnQg%3D%3D&notify_url=http%3A%2F%2Flocallhost%3A8888&version=1.0&app_id=2021000121668200&sign_type=RSA2&timestamp=2022-09-19+12%3A04%3A22&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json">
        <input type="hidden" name="biz_content" value="{&quot;body&quot;:&quot;test&quot;,&quot;out_trade_no&quot;:&quot;10003&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;,&quot;subject&quot;:&quot;product1&quot;,&quot;timeout_express&quot;:&quot;10m&quot;,&quot;total_amount&quot;:&quot;100&quot;}">
        <input type="submit" value="立即支付" >
        </form>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                
            }
        },methods: {
            
        },
    }
</script>

<style lang="scss" scoped>

</style>