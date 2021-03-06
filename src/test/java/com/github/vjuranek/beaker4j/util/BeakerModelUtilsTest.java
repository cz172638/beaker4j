package com.github.vjuranek.beaker4j.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.vjuranek.beaker4j.model.Job;
import com.github.vjuranek.beaker4j.model.Task;
import com.github.vjuranek.beaker4j.remote_model.TaskResult;

public class BeakerModelUtilsTest {

    private static Job job;
    
    @BeforeClass
    public static void loadJob() throws JAXBException, URISyntaxException {
        File file  = new File(Job.class.getResource("job.xml").toURI());
        JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        job = (Job) jaxbUnmarshaller.unmarshal(file);
    }
    
    @Test
    public void getTaskById() {
        Task task = BeakerModelUtils.getJobTaskById(job, 687991);
        assertEquals("/distribution/reservesys", task.getName());
        assertEquals(TaskResult.WARN, TaskResult.valueOf(task.getResult().toUpperCase()));
    }
    
    @Test
    public void getTaskName() {
        Task task = BeakerModelUtils.getJobTaskByName(job, "/distribution/reservesys");
        assertEquals(687991, Integer.decode(task.getId()).intValue());
        assertEquals(TaskResult.WARN, TaskResult.valueOf(task.getResult().toUpperCase()));
    }
    
}
