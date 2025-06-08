package com.sdjyyds.product.controller;
import com.sdjyyds.product.entity.Merchant;
import com.sdjyyds.product.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/Merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @DeleteMapping("/{id}")
    public int deleteByPrimaryKey(@PathVariable Long id) {
        return merchantService.deleteByPrimaryKey(id);
    }

    @PostMapping
    public int insert(@RequestBody Merchant record) {
        return merchantService.insert(record);
    }

    @PostMapping("/selective")
    public int insertSelective(@RequestBody Merchant record) {
        return merchantService.insertSelective(record);
    }

    @GetMapping("/{id}")
    public Merchant selectByPrimaryKey(@PathVariable Long id) {
        return merchantService.selectByPrimaryKey(id);
    }

    @GetMapping
    public List<Merchant> selectAll() {
        return merchantService.selectAll();
    }

    @PutMapping
    public int updateByPrimaryKeySelective(@RequestBody Merchant record) {
        return merchantService.updateByPrimaryKeySelective(record);
    }

    @PutMapping("/full")
    public int updateByPrimaryKey(@RequestBody Merchant record) {
        return merchantService.updateByPrimaryKey(record);
    }
}    
