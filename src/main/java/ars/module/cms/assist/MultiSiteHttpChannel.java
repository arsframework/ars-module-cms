package ars.module.cms.assist;

import java.io.File;

import ars.util.Strings;
import ars.module.cms.model.Site;
import ars.invoke.channel.http.Https;
import ars.invoke.channel.http.HttpRequester;
import ars.invoke.channel.http.StandardHttpChannel;
import ars.database.repository.Repository;
import ars.database.repository.Repositories;

/**
 * 多站点HTTP请求通道实现
 *
 * @author wuyongqiang
 */
public class MultiSiteHttpChannel extends StandardHttpChannel {
    private String staticizeDirectory = Strings.TEMP_PATH; // 静态化文件目录

    public String getStaticizeDirectory() {
        return staticizeDirectory;
    }

    public void setStaticizeDirectory(String staticizeDirectory) {
        this.staticizeDirectory = Strings.getRealPath(staticizeDirectory);
    }

    @Override
    protected String lookupTemplate(HttpRequester requester) {
        if (requester.getUri().equals(Https.ROOT_URI)) {
            String domain = requester.getHttpServletRequest().getServerName();
            Repository<Site> repository = Repositories.getRepository(Site.class);
            Site site = repository.query().eq("domain", domain).single();
            if (site != null && site.getTemplate() != null) {
                File file = new File(this.staticizeDirectory, new StringBuilder(domain).append(".html").toString());
                if (!site.getStaticize() || !file.exists()) {
                    synchronized (domain.intern()) {
                        if (!site.getStaticize()) {
                            site = repository.query().eq("domain", domain).single();
                        }
                        if (!site.getStaticize() || !file.exists()) {
                            try {
                                requester.render(site.getTemplate(), site, file);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            if (!site.getStaticize()) {
                                site.setStaticize(true);
                                repository.update(site);
                            }
                        }
                    }
                }
                return file.getPath();
            }
        }
        return super.lookupTemplate(requester);
    }

}
