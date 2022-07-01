package com.virtualpowerplant.dao;

import com.virtualpowerplant.VirtualpowerplantApplicationTests;
import com.virtualpowerplant.dao.entity.Battery;
import com.virtualpowerplant.dao.entity.BatteryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class BatteryRepositoryTests extends VirtualpowerplantApplicationTests {

    @Autowired
    BatteryRepository batteryRepository;

    @Test
    public void testFindPostcodeBetweenNative() {
        Battery b_1 = new Battery("test_1", 6000, 500);
        Battery b_2 = new Battery("test_4", 7000, 600);
        Battery b_3 = new Battery("test_3", 8000, 700);
        Battery b_4 = new Battery("test_2", 9000, 800);
        Battery b_5 = new Battery("test_5", 10000, 900);

        List<Battery> list = new ArrayList<>();
        list.add(b_1);
        list.add(b_2);
        list.add(b_3);
        list.add(b_4);
        list.add(b_5);

        batteryRepository.saveAll(list);
        list.clear();

        assertThat(batteryRepository.findAll().size()).isEqualTo(5);
        list = batteryRepository.findPostcodeBetweenNative(7000, 9000);
        assertThat(list.get(0).getName()).isEqualTo("test_2");
        assertThat(list.get(1).getName()).isEqualTo("test_3");
        assertThat(list.get(2).getName()).isEqualTo("test_4");
        assertThat(list.size()).isEqualTo(3);
    }

}
