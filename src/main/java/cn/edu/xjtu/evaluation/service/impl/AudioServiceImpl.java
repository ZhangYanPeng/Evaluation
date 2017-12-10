package cn.edu.xjtu.evaluation.service.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.xjtu.evaluation.dao.impl.AudioDAOImpl;
import cn.edu.xjtu.evaluation.entity.Audio;
import cn.edu.xjtu.evaluation.service.IAudioService;

@Service
public class AudioServiceImpl implements IAudioService{

	@Autowired
	AudioDAOImpl audioDAO;
	
	@Override
	@Transactional
	public int save(Audio audio, MultipartFile file) {
		// TODO Auto-generated method stub
		if(file.isEmpty())
			return -1;
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(audio.getPath()));
			audioDAO.save(audio);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	@Transactional
	public int delete(long id) {
		// TODO Auto-generated method stub
		File file = new File(audioDAO.get(id).getPath());
		if (file.exists()) {
			file.delete();
		}
		audioDAO.deleteById(id);
		return 1;
	}

}
