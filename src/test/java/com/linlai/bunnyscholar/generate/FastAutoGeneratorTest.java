package com.linlai.bunnyscholar.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.junit.jupiter.api.Test;
import com.baomidou.mybatisplus.annotation.FieldFill;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastAutoGeneratorTest {
    @Test
    public void generate () {


        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test", "root", "91yawZf=0S(R")
                .globalConfig(builder -> {
                    builder.author("linlai") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("G:\\workspace\\bunny-scholar\\src\\main\\java\\com\\linlai\\bunnyscholar"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "G:\\workspace\\bunny-scholar\\src\\main\\resources")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("record") // 设置需要生成的表名
                            .entityBuilder().enableFileOverride()
                            .mapperBuilder().entityBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();



    }
    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
