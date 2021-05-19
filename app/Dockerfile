FROM centos
VOLUME /tmp
RUN mkdir -p /projetos
ADD app/target/crudCliente-0.0.1-SNAPSHOT.jar app/app.jar
RUN yum install -y java-11-openjdk-devel
RUN yum install -y glibc-locale-source
RUN localedef -i pt_BR -f UTF-8 pt_BR.UTF-8
ENV LANG=pt_BR.UTF-8
ENV LC_ALL=pt_BR.UTF-8
RUN rm -rf /etc/localtime
RUN ln -s /usr/share/zoneinfo/America/Fortaleza /etc/localtime