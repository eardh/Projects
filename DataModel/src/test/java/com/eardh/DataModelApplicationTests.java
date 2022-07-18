package com.eardh;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.MapWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.validation.ValidationUtil;
import com.eardh.model.*;
import com.eardh.model.enums.DataType;
import com.eardh.model.enums.IEnum;
import com.eardh.model.enums.SearchType;
import com.eardh.utils.BeanUtils;
import com.eardh.utils.Constant;
import com.eardh.utils.ValidateUtil;
import com.eardh.validate.Crud;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.steppschuh.markdowngenerator.MarkdownBuilder;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.TextBuilder;
import net.steppschuh.markdowngenerator.text.code.CodeBlock;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.emphasis.ItalicText;
import net.steppschuh.markdowngenerator.text.emphasis.StrikeThroughText;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanMap;

import javax.validation.Validation;
import javax.validation.groups.Default;
import java.beans.Beans;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.steppschuh.markdowngenerator.Markdown.bold;
import static net.steppschuh.markdowngenerator.Markdown.italic;

@SpringBootTest
class DataModelApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
    }

}
