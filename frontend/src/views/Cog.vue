<!--反馈页面-->
<template>
	<v-app
	id="cog"
	>
		<v-app-bar
		app
		color="white"
		elevate-on-scroll
        >
            <v-btn icon color="blue-grey darken-4" x-large to="/user">
                <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
		</v-app-bar>
		<v-main style="margin-top: 0px;">
			<v-banner
			single-line>
				意见反馈
				<template v-slot:actions>
					<v-btn
					icon>
						<v-icon large>mdi-chevron-right</v-icon>
					</v-btn>
				</template>
			</v-banner>
			<v-banner
			single-line>
				关于北大知道
				<template v-slot:actions>
					<v-btn
					icon>
						<v-icon large>mdi-chevron-right</v-icon>
					</v-btn>
				</template>
			</v-banner>
			<v-row justify="center" align="center" style="margin-top: 40px;">
				<v-btn
				text
				color="red"
				to="/"
				>退出当前账号</v-btn>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>
	export default{
		name:'Cog',
	}
</script>

<style>
</style>
