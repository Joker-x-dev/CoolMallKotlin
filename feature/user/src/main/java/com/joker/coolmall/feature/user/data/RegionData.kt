package com.joker.coolmall.feature.user.data

import com.joker.coolmall.feature.user.model.Region

/**
 * 地区数据
 *
 * @author Joker.X
 */
object RegionData {

    /**
     * 获取所有省级地区数据
     *
     * @return 省级地区列表
     * @author Joker.X
     */
    fun getProvinces(): List<Region> {
        return listOf(
            Region(
                id = "11",
                name = "北京市",
                children = listOf(
                    Region(
                        id = "1101",
                        name = "北京市",
                        children = listOf(
                            Region(id = "110101", name = "东城区"),
                            Region(id = "110102", name = "西城区"),
                            Region(id = "110105", name = "朝阳区"),
                            Region(id = "110106", name = "丰台区"),
                            Region(id = "110108", name = "海淀区"),
                            Region(id = "110109", name = "门头沟区"),
                            Region(id = "110111", name = "房山区"),
                            Region(id = "110112", name = "通州区"),
                            Region(id = "110113", name = "顺义区"),
                            Region(id = "110114", name = "昌平区"),
                            Region(id = "110115", name = "大兴区"),
                            Region(id = "110116", name = "怀柔区"),
                            Region(id = "110117", name = "平谷区"),
                            Region(id = "110118", name = "密云区"),
                            Region(id = "110119", name = "延庆区")
                        )
                    )
                )
            ),
            Region(
                id = "12",
                name = "天津市",
                children = listOf(
                    Region(
                        id = "1201",
                        name = "天津市",
                        children = listOf(
                            Region(id = "120101", name = "和平区"),
                            Region(id = "120102", name = "河东区"),
                            Region(id = "120103", name = "河西区"),
                            Region(id = "120104", name = "南开区"),
                            Region(id = "120105", name = "河北区"),
                            Region(id = "120106", name = "红桥区"),
                            Region(id = "120110", name = "东丽区"),
                            Region(id = "120111", name = "西青区"),
                            Region(id = "120112", name = "津南区"),
                            Region(id = "120113", name = "北辰区"),
                            Region(id = "120114", name = "武清区"),
                            Region(id = "120115", name = "宝坻区"),
                            Region(id = "120116", name = "滨海新区"),
                            Region(id = "120117", name = "宁河区"),
                            Region(id = "120118", name = "静海区"),
                            Region(id = "120119", name = "蓟州区")
                        )
                    )
                )
            ),
            Region(
                id = "13",
                name = "河北省",
                children = listOf(
                    Region(
                        id = "1301",
                        name = "石家庄市",
                        children = listOf(
                            Region(id = "130102", name = "长安区"),
                            Region(id = "130104", name = "桥西区"),
                            Region(id = "130105", name = "新华区"),
                            Region(id = "130107", name = "井陉矿区"),
                            Region(id = "130108", name = "裕华区"),
                            Region(id = "130109", name = "藁城区"),
                            Region(id = "130110", name = "鹿泉区"),
                            Region(id = "130111", name = "栾城区"),
                            Region(id = "130121", name = "井陉县"),
                            Region(id = "130123", name = "正定县"),
                            Region(id = "130125", name = "行唐县"),
                            Region(id = "130126", name = "灵寿县"),
                            Region(id = "130127", name = "高邑县"),
                            Region(id = "130128", name = "深泽县"),
                            Region(id = "130129", name = "赞皇县")
                        )
                    ),
                    Region(
                        id = "1302",
                        name = "唐山市",
                        children = listOf(
                            Region(id = "130202", name = "路南区"),
                            Region(id = "130203", name = "路北区"),
                            Region(id = "130204", name = "古冶区"),
                            Region(id = "130205", name = "开平区"),
                            Region(id = "130207", name = "丰南区"),
                            Region(id = "130208", name = "丰润区"),
                            Region(id = "130209", name = "曹妃甸区"),
                            Region(id = "130224", name = "滦南县"),
                            Region(id = "130225", name = "乐亭县"),
                            Region(id = "130227", name = "迁西县")
                        )
                    ),
                    Region(
                        id = "1303",
                        name = "秦皇岛市",
                        children = listOf(
                            Region(id = "130302", name = "海港区"),
                            Region(id = "130303", name = "山海关区"),
                            Region(id = "130304", name = "北戴河区"),
                            Region(id = "130306", name = "抚宁区"),
                            Region(id = "130321", name = "青龙满族自治县"),
                            Region(id = "130322", name = "昌黎县"),
                            Region(id = "130324", name = "卢龙县")
                        )
                    )
                )
            ),
            Region(
                id = "14",
                name = "山西省",
                children = listOf(
                    Region(
                        id = "1401",
                        name = "太原市",
                        children = listOf(
                            Region(id = "140105", name = "小店区"),
                            Region(id = "140106", name = "迎泽区"),
                            Region(id = "140107", name = "杏花岭区"),
                            Region(id = "140108", name = "尖草坪区"),
                            Region(id = "140109", name = "万柏林区"),
                            Region(id = "140110", name = "晋源区"),
                            Region(id = "140121", name = "清徐县"),
                            Region(id = "140122", name = "阳曲县"),
                            Region(id = "140123", name = "娄烦县"),
                            Region(id = "140181", name = "古交市")
                        )
                    ),
                    Region(
                        id = "1402",
                        name = "大同市",
                        children = listOf(
                            Region(id = "140212", name = "新荣区"),
                            Region(id = "140213", name = "平城区"),
                            Region(id = "140214", name = "云冈区"),
                            Region(id = "140215", name = "云州区"),
                            Region(id = "140221", name = "阳高县"),
                            Region(id = "140222", name = "天镇县"),
                            Region(id = "140223", name = "广灵县"),
                            Region(id = "140224", name = "灵丘县"),
                            Region(id = "140225", name = "浑源县"),
                            Region(id = "140226", name = "左云县")
                        )
                    )
                )
            ),
            Region(
                id = "31",
                name = "上海市",
                children = listOf(
                    Region(
                        id = "3101",
                        name = "上海市",
                        children = listOf(
                            Region(id = "310101", name = "黄浦区"),
                            Region(id = "310104", name = "徐汇区"),
                            Region(id = "310105", name = "长宁区"),
                            Region(id = "310106", name = "静安区"),
                            Region(id = "310107", name = "普陀区"),
                            Region(id = "310109", name = "虹口区"),
                            Region(id = "310110", name = "杨浦区"),
                            Region(id = "310112", name = "闵行区"),
                            Region(id = "310113", name = "宝山区"),
                            Region(id = "310114", name = "嘉定区"),
                            Region(id = "310115", name = "浦东新区"),
                            Region(id = "310116", name = "金山区"),
                            Region(id = "310117", name = "松江区"),
                            Region(id = "310118", name = "青浦区"),
                            Region(id = "310120", name = "奉贤区"),
                            Region(id = "310151", name = "崇明区")
                        )
                    )
                )
            ),
            Region(
                id = "32",
                name = "江苏省",
                children = listOf(
                    Region(
                        id = "3201",
                        name = "南京市",
                        children = listOf(
                            Region(id = "320102", name = "玄武区"),
                            Region(id = "320104", name = "秦淮区"),
                            Region(id = "320105", name = "建邺区"),
                            Region(id = "320106", name = "鼓楼区"),
                            Region(id = "320111", name = "浦口区"),
                            Region(id = "320113", name = "栖霞区"),
                            Region(id = "320114", name = "雨花台区"),
                            Region(id = "320115", name = "江宁区"),
                            Region(id = "320116", name = "六合区"),
                            Region(id = "320117", name = "溧水区"),
                            Region(id = "320118", name = "高淳区")
                        )
                    ),
                    Region(
                        id = "3202",
                        name = "无锡市",
                        children = listOf(
                            Region(id = "320205", name = "锡山区"),
                            Region(id = "320206", name = "惠山区"),
                            Region(id = "320211", name = "滨湖区"),
                            Region(id = "320213", name = "梁溪区"),
                            Region(id = "320214", name = "新吴区"),
                            Region(id = "320281", name = "江阴市"),
                            Region(id = "320282", name = "宜兴市")
                        )
                    ),
                    Region(
                        id = "3203",
                        name = "徐州市",
                        children = listOf(
                            Region(id = "320302", name = "鼓楼区"),
                            Region(id = "320303", name = "云龙区"),
                            Region(id = "320305", name = "贾汪区"),
                            Region(id = "320311", name = "泉山区"),
                            Region(id = "320312", name = "铜山区"),
                            Region(id = "320321", name = "丰县"),
                            Region(id = "320322", name = "沛县"),
                            Region(id = "320324", name = "睢宁县"),
                            Region(id = "320381", name = "新沂市"),
                            Region(id = "320382", name = "邳州市")
                        )
                    ),
                    Region(
                        id = "3204",
                        name = "常州市",
                        children = listOf(
                            Region(id = "320402", name = "天宁区"),
                            Region(id = "320404", name = "钟楼区"),
                            Region(id = "320411", name = "新北区"),
                            Region(id = "320412", name = "武进区"),
                            Region(id = "320413", name = "金坛区"),
                            Region(id = "320481", name = "溧阳市")
                        )
                    )
                )
            ),
            Region(
                id = "33",
                name = "浙江省",
                children = listOf(
                    Region(
                        id = "3301",
                        name = "杭州市",
                        children = listOf(
                            Region(id = "330102", name = "上城区"),
                            Region(id = "330103", name = "下城区"),
                            Region(id = "330104", name = "江干区"),
                            Region(id = "330105", name = "拱墅区"),
                            Region(id = "330106", name = "西湖区"),
                            Region(id = "330108", name = "滨江区"),
                            Region(id = "330109", name = "萧山区"),
                            Region(id = "330110", name = "余杭区"),
                            Region(id = "330111", name = "富阳区"),
                            Region(id = "330112", name = "临安区"),
                            Region(id = "330122", name = "桐庐县"),
                            Region(id = "330127", name = "淳安县"),
                            Region(id = "330182", name = "建德市")
                        )
                    ),
                    Region(
                        id = "3302",
                        name = "宁波市",
                        children = listOf(
                            Region(id = "330203", name = "海曙区"),
                            Region(id = "330205", name = "江北区"),
                            Region(id = "330206", name = "北仑区"),
                            Region(id = "330211", name = "镇海区"),
                            Region(id = "330212", name = "鄞州区"),
                            Region(id = "330213", name = "奉化区"),
                            Region(id = "330225", name = "象山县"),
                            Region(id = "330226", name = "宁海县"),
                            Region(id = "330281", name = "余姚市"),
                            Region(id = "330282", name = "慈溪市")
                        )
                    )
                )
            ),
            Region(
                id = "34",
                name = "安徽省",
                children = listOf(
                    Region(
                        id = "3401",
                        name = "合肥市",
                        children = listOf(
                            Region(id = "340102", name = "瑶海区"),
                            Region(id = "340103", name = "庐阳区"),
                            Region(id = "340104", name = "蜀山区"),
                            Region(id = "340111", name = "包河区"),
                            Region(id = "340121", name = "长丰县"),
                            Region(id = "340122", name = "肥东县"),
                            Region(id = "340123", name = "肥西县"),
                            Region(id = "340124", name = "庐江县"),
                            Region(id = "340181", name = "巢湖市")
                        )
                    )
                )
            ),
            Region(
                id = "44",
                name = "广东省",
                children = listOf(
                    Region(
                        id = "4401",
                        name = "广州市",
                        children = listOf(
                            Region(id = "440103", name = "荔湾区"),
                            Region(id = "440104", name = "越秀区"),
                            Region(id = "440105", name = "海珠区"),
                            Region(id = "440106", name = "天河区"),
                            Region(id = "440111", name = "白云区"),
                            Region(id = "440112", name = "黄埔区"),
                            Region(id = "440113", name = "番禺区"),
                            Region(id = "440114", name = "花都区"),
                            Region(id = "440115", name = "南沙区"),
                            Region(id = "440117", name = "从化区"),
                            Region(id = "440118", name = "增城区")
                        )
                    ),
                    Region(
                        id = "4403",
                        name = "深圳市",
                        children = listOf(
                            Region(id = "440303", name = "罗湖区"),
                            Region(id = "440304", name = "福田区"),
                            Region(id = "440305", name = "南山区"),
                            Region(id = "440306", name = "宝安区"),
                            Region(id = "440307", name = "龙岗区"),
                            Region(id = "440308", name = "盐田区"),
                            Region(id = "440309", name = "龙华区"),
                            Region(id = "440310", name = "坪山区"),
                            Region(id = "440311", name = "光明区")
                        )
                    ),
                    Region(
                        id = "4406",
                        name = "佛山市",
                        children = listOf(
                            Region(id = "440604", name = "禅城区"),
                            Region(id = "440605", name = "南海区"),
                            Region(id = "440606", name = "顺德区"),
                            Region(id = "440607", name = "三水区"),
                            Region(id = "440608", name = "高明区")
                        )
                    ),
                    Region(
                        id = "4413",
                        name = "惠州市",
                        children = listOf(
                            Region(id = "441302", name = "惠城区"),
                            Region(id = "441303", name = "惠阳区"),
                            Region(id = "441322", name = "博罗县"),
                            Region(id = "441323", name = "惠东县"),
                            Region(id = "441324", name = "龙门县")
                        )
                    )
                )
            ),
            Region(
                id = "45",
                name = "广西壮族自治区",
                children = listOf(
                    Region(
                        id = "4501",
                        name = "南宁市",
                        children = listOf(
                            Region(id = "450102", name = "兴宁区"),
                            Region(id = "450103", name = "青秀区"),
                            Region(id = "450105", name = "江南区"),
                            Region(id = "450107", name = "西乡塘区"),
                            Region(id = "450108", name = "良庆区"),
                            Region(id = "450109", name = "邕宁区"),
                            Region(id = "450110", name = "武鸣区"),
                            Region(id = "450123", name = "隆安县"),
                            Region(id = "450124", name = "马山县"),
                            Region(id = "450125", name = "上林县"),
                            Region(id = "450126", name = "宾阳县"),
                            Region(id = "450127", name = "横县")
                        )
                    ),
                    Region(
                        id = "4502",
                        name = "柳州市",
                        children = listOf(
                            Region(id = "450202", name = "城中区"),
                            Region(id = "450203", name = "鱼峰区"),
                            Region(id = "450204", name = "柳南区"),
                            Region(id = "450205", name = "柳北区"),
                            Region(id = "450206", name = "柳江区"),
                            Region(id = "450222", name = "柳城县"),
                            Region(id = "450223", name = "鹿寨县"),
                            Region(id = "450224", name = "融安县"),
                            Region(id = "450225", name = "融水苗族自治县"),
                            Region(id = "450226", name = "三江侗族自治县")
                        )
                    ),
                    Region(
                        id = "4507",
                        name = "钦州市",
                        children = listOf(
                            Region(id = "450702", name = "钦南区"),
                            Region(id = "450703", name = "钦北区"),
                            Region(id = "450721", name = "灵山县"),
                            Region(id = "450722", name = "浦北县")
                        )
                    ),
                    Region(
                        id = "4507",
                        name = "崇左市",
                        children = listOf(
                            Region(id = "450702", name = "江州区"),
                            Region(id = "450721", name = "大新县"),
                            Region(id = "450722", name = "扶绥县"),
                            Region(id = "450723", name = "龙州县"),
                            Region(id = "450724", name = "宁明县"),
                            Region(id = "450725", name = "天等县")
                        )
                    ),
                    Region(
                        id = "4512",
                        name = "河池市",
                        children = listOf(
                            Region(id = "451202", name = "金城江区"),
                            Region(id = "451221", name = "南丹县"),
                            Region(id = "451222", name = "天峨县"),
                            Region(id = "451223", name = "凤山县"),
                            Region(id = "451224", name = "东兰县"),
                            Region(id = "451225", name = "罗城仫佬族自治县"),
                            Region(id = "451226", name = "环江毛南族自治县"),
                            Region(id = "451227", name = "巴马瑶族自治县"),
                            Region(id = "451228", name = "都安瑶族自治县"),
                            Region(id = "451229", name = "大化瑶族自治县")
                        )
                    )
                )
            ),
            Region(
                id = "51",
                name = "四川省",
                children = listOf(
                    Region(
                        id = "5101",
                        name = "成都市",
                        children = listOf(
                            Region(id = "510104", name = "锦江区"),
                            Region(id = "510105", name = "青羊区"),
                            Region(id = "510106", name = "金牛区"),
                            Region(id = "510107", name = "武侯区"),
                            Region(id = "510108", name = "成华区"),
                            Region(id = "510112", name = "龙泉驿区"),
                            Region(id = "510113", name = "青白江区"),
                            Region(id = "510114", name = "新都区"),
                            Region(id = "510115", name = "温江区"),
                            Region(id = "510116", name = "双流区"),
                            Region(id = "510117", name = "郫都区"),
                            Region(id = "510118", name = "新津区"),
                            Region(id = "510121", name = "金堂县"),
                            Region(id = "510129", name = "大邑县"),
                            Region(id = "510131", name = "蒲江县")
                        )
                    )
                )
            ),
            Region(
                id = "52",
                name = "贵州省",
                children = listOf(
                    Region(
                        id = "5201",
                        name = "贵阳市",
                        children = listOf(
                            Region(id = "520102", name = "南明区"),
                            Region(id = "520103", name = "云岩区"),
                            Region(id = "520111", name = "花溪区"),
                            Region(id = "520112", name = "乌当区"),
                            Region(id = "520113", name = "白云区"),
                            Region(id = "520115", name = "观山湖区"),
                            Region(id = "520121", name = "开阳县"),
                            Region(id = "520122", name = "息烽县"),
                            Region(id = "520123", name = "修文县")
                        )
                    )
                )
            ),
            Region(
                id = "61",
                name = "陕西省",
                children = listOf(
                    Region(
                        id = "6101",
                        name = "西安市",
                        children = listOf(
                            Region(id = "610102", name = "新城区"),
                            Region(id = "610103", name = "碑林区"),
                            Region(id = "610104", name = "莲湖区"),
                            Region(id = "610111", name = "灞桥区"),
                            Region(id = "610112", name = "未央区"),
                            Region(id = "610113", name = "雁塔区"),
                            Region(id = "610114", name = "阎良区"),
                            Region(id = "610115", name = "临潼区"),
                            Region(id = "610116", name = "长安区"),
                            Region(id = "610117", name = "高陵区"),
                            Region(id = "610118", name = "鄠邑区"),
                            Region(id = "610122", name = "蓝田县"),
                            Region(id = "610124", name = "周至县")
                        )
                    )
                )
            )
        )
    }
} 