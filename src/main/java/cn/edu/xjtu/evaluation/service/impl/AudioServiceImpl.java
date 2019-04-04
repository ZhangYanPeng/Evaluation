package cn.edu.xjtu.evaluation.service.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.xjtu.evaluation.dao.impl.AudioDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.entity.Audio;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.service.IAudioService;

@Service
public class AudioServiceImpl implements IAudioService{

	@Autowired
	AudioDAOImpl audioDAO;
	@Autowired
	QuestionDAOImpl questionDAO;
	@Autowired
	InterventionDAOImpl interventionDAO;
	@Autowired
	PartDAOImpl partDAO;
	
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

	@Override
	@Transactional
	public Audio getByQue(long id) {
		// TODO Auto-generated method stub
		String hql = "from Audio where question.id = ?";
		Object[] values = {id};
		return audioDAO.getByHQL(hql, values);
	}
	
	@Override
	@Transactional
	public Audio getByPa(long id) {
		// TODO Auto-generated method stub
		String hql = "from Audio where part.id = ?";
		Object[] values = {id};
		return audioDAO.getByHQL(hql, values);
	}

	@Override
	@Transactional
	public Audio getByIn(long id) {
		// TODO Auto-generated method stub
		String hql = "from Audio where intervention.id = ?";
		Object[] values = {id};
		return audioDAO.getByHQL(hql, values);
	}

	@Override
	@Transactional
	public int removeAud(Long aud_id) {
		// TODO Auto-generated method stub
		Audio audio = audioDAO.get(aud_id);
		if(audio.getQuestion() != null){
			Question q = audio.getQuestion();
			q.setAudio(null);
			questionDAO.update(q);
		}else if(audio.getIntervention() !=null){
			Intervention i = audio.getIntervention();
			i.setAudio(null);
			interventionDAO.update(i);
		}else if(audio.getPart() !=null){
			Part p = audio.getPart();
			p.setDirectAudio(null);
			partDAO.update(p);
		}
		File file=new File(audio.getPath());
        if( file.exists()&&file.isFile() )
            file.delete();
		audioDAO.delete(audio);
		return 0;
	}
}
