FROM maven:3.5.2

WORKDIR /app
ADD . /app

EXPOSE 80

CMD ["/app/run.sh"]

